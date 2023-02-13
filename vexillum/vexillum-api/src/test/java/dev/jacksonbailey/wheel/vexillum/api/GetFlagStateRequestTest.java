package dev.jacksonbailey.wheel.vexillum.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class GetFlagStateRequestTest {

  @Test
  void test() throws Exception {
    var name = "foo";
    var expected = GetFlagStateRequest.newBuilder().setName(name).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = GetFlagStateRequest.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(name, actual.getName()),
        () -> assertEquals(actual, expected)
    );
  }

}
