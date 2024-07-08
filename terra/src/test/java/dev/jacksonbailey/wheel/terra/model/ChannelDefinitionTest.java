package dev.jacksonbailey.wheel.terra.model;

import static dev.jacksonbailey.wheel.terra.TestUtils.mockTextChannel;
import static dev.jacksonbailey.wheel.terra.TestUtils.mockVoiceChannel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ChannelDefinitionTest {

  @Test
  void testTextChannel() {
    var textChannelName = "text channel";
    var textChannel = mockTextChannel(textChannelName);

    var definition = ChannelDefinition.from(textChannel);

    assertAll(
        () -> assertThat(definition).isInstanceOf(TextChannelDefinition.class),
        () -> assertEquals(textChannelName, definition.name())
    );
  }

  @Test
  void testVoiceChannel() {
    var voiceChannelName = "voice channel";
    var voiceChannel = mockVoiceChannel(voiceChannelName);

    var definition = ChannelDefinition.from(voiceChannel);

    assertAll(
        () -> assertThat(definition).isInstanceOf(VoiceChannelDefinition.class),
        () -> assertEquals(voiceChannelName, definition.name())
    );
  }

}
