package dev.jacksonbailey.wheel.vexillum.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class SetFlagStateReplyTest {

  @Test
  void test() throws Exception {
    var name = "foo";
    var state = true;
    var previousFlag = FlagMother.makeFlag(name, state);
    var expected = SetFlagStateReply.newBuilder().setPreviousFlag(previousFlag).build();
    var byteStream = new ByteArrayOutputStream();
    expected.writeTo(byteStream);
    var actual = SetFlagStateReply.parseFrom(byteStream.toByteArray());
    assertAll(
        () -> assertEquals(previousFlag, actual.getPreviousFlag()),
        () -> assertEquals(actual, expected)
    );
  }

}
