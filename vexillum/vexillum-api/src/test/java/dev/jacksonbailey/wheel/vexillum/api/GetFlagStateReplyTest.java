package dev.jacksonbailey.wheel.vexillum.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class GetFlagStateReplyTest {

  @Test
  void test() throws Exception {
    var name = "foo";
    var state = true;
    var currentFlag = FlagMother.makeFlag(name, state);
    var expected = GetFlagStateReply.newBuilder().setCurrentFlag(currentFlag).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = GetFlagStateReply.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(currentFlag, actual.getCurrentFlag()),
        () -> assertEquals(actual, expected)
    );
  }

}
