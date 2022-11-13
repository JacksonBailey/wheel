package dev.jacksonbailey.wheel.vexillum.protos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class HelloRequestTest {

  @Test
  void test() throws Exception {
    var name = "Jackson";
    var expected = HelloRequest.newBuilder().setName(name).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = HelloRequest.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(name, actual.getName()),
        () -> assertEquals(actual, expected)
    );
  }

}
