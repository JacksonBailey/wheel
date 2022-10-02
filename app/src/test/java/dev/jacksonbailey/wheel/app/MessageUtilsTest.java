package dev.jacksonbailey.wheel.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessageUtilsTest {

  @Test
  void testGetMessage() {
    assertEquals("Hello      World!", MessageUtils.getMessage());
  }
}
