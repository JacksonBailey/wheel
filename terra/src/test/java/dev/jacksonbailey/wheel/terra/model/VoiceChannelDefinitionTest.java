package dev.jacksonbailey.wheel.terra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.junit.jupiter.api.Test;

class VoiceChannelDefinitionTest {

  @Test
  void test() {
    var voiceChannel = mock(VoiceChannel.class);
    var voiceChannelName = "voice channel";
    given(voiceChannel.getName()).willReturn(voiceChannelName);
    given(voiceChannel.getType()).willReturn(ChannelType.VOICE);

    var definition = ChannelDefinition.from(voiceChannel);

    assertEquals(voiceChannelName, definition.name());
  }

}
