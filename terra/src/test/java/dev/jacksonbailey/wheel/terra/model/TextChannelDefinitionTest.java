package dev.jacksonbailey.wheel.terra.model;

import static dev.jacksonbailey.wheel.terra.TestUtils.mockTextChannel;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextChannelDefinitionTest {

  @Test
  void test() {
    var textChannelName = "text channel";
    var textChannel = mockTextChannel(textChannelName);

    var definition = TextChannelDefinition.from(textChannel);

    assertEquals(textChannelName, definition.name());
  }

}
