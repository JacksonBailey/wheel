package dev.jacksonbailey.wheel.terra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import dev.jacksonbailey.wheel.terra.model.ChannelDefinition;
import java.util.List;
import java.util.Set;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetChannelsCommandTest {

  @Mock
  private JDA jda;

  private final GetChannelsCommand getChannelsCommand = new GetChannelsCommand(jda);

  @Test
  void test() {
    var event = mock(SlashCommandInteractionEvent.class);
    var guild = mock(Guild.class);
    given(event.getGuild()).willReturn(guild);
    var textChannel = mock(TextChannel.class);
    var textChannelName = "text channel";
    given(textChannel.getName()).willReturn(textChannelName);
    given(textChannel.getType()).willReturn(ChannelType.TEXT);
    List<GuildChannel> channels = List.of(textChannel);
    given(guild.getChannels()).willReturn(channels);

    try (var messageCreateData = getChannelsCommand.doCommand(event)) {
      assertEquals(
          Set.of(ChannelDefinition.from(textChannel)).toString(),
          messageCreateData.getContent()
      );
    }
  }

}
