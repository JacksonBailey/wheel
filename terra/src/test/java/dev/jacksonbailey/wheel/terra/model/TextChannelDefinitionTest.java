package dev.jacksonbailey.wheel.terra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.junit.jupiter.api.Test;

class TextChannelDefinitionTest {

  @Test
  void test() {
    var textChannel = mock(TextChannel.class);
    var textChannelName = "text channel";
    given(textChannel.getName()).willReturn(textChannelName);
    given(textChannel.getType()).willReturn(ChannelType.TEXT);

    var definition = TextChannelDefinition.from(textChannel);

    assertEquals(textChannelName, definition.name());
  }

}
