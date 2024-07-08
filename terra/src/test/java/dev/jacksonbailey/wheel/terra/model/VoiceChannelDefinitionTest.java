package dev.jacksonbailey.wheel.terra.model;

import static dev.jacksonbailey.wheel.terra.TestUtils.mockVoiceChannel;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VoiceChannelDefinitionTest {

  @Test
  void test() {
    var voiceChannelName = "voice channel";
    var voiceChannel = mockVoiceChannel(voiceChannelName);

    var definition = ChannelDefinition.from(voiceChannel);

    assertEquals(voiceChannelName, definition.name());
  }

}
