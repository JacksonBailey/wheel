package dev.jacksonbailey.wheel.vexillum.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class HelloReplyTest {

  @Test
  void test() throws Exception {
    var message = "Howdy!";
    var expected = HelloReply.newBuilder().setMessage(message).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = HelloReply.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(message, actual.getMessage()),
        () -> assertEquals(actual, expected)
    );
  }

}
