package dev.jacksonbailey.wheel.vexillum.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class FlagTest {

  @Test
  void test() throws Exception {
    var name = "foo";
    var state = true;
    var expected = Flag.newBuilder().setName(name).setState(state).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = Flag.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(name, actual.getName()),
        () -> assertEquals(state, actual.getState()),
        () -> assertEquals(actual, expected)
    );
  }

}
