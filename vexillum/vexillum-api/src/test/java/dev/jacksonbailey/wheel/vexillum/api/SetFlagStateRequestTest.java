package dev.jacksonbailey.wheel.vexillum.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class SetFlagStateRequestTest {

  @Test
  void test() throws Exception {
    var name = "foo";
    var state = true;
    var newFlag = FlagMother.makeFlag(name, state);
    var expected = SetFlagStateRequest.newBuilder().setNewFlag(newFlag).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = SetFlagStateRequest.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(newFlag, actual.getNewFlag()),
        () -> assertEquals(actual, expected)
    );
  }

}
