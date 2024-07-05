package dev.jacksonbailey.wheel.terra.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.junit.jupiter.api.Test;

class ChannelDefinitionTest {

  @Test
  void testTextChannel() {
    var textChannel = mock(TextChannel.class);
    var textChannelName = "text channel";
    given(textChannel.getName()).willReturn(textChannelName);
    given(textChannel.getType()).willReturn(ChannelType.TEXT);

    var definition = ChannelDefinition.from(textChannel);

    assertAll(
        () -> assertThat(definition).isInstanceOf(TextChannelDefinition.class),
        () -> assertEquals(textChannelName, definition.name())
    );
  }

  @Test
  void testVoiceChannel() {
    var voiceChannel = mock(VoiceChannel.class);
    var voiceChannelName = "voice channel";
    given(voiceChannel.getName()).willReturn(voiceChannelName);
    given(voiceChannel.getType()).willReturn(ChannelType.VOICE);

    var definition = ChannelDefinition.from(voiceChannel);

    assertAll(
        () -> assertThat(definition).isInstanceOf(VoiceChannelDefinition.class),
        () -> assertEquals(voiceChannelName, definition.name())
    );
  }

}
