package dev.jacksonbailey.wheel.collections;

import static dev.jacksonbailey.wheel.collections.TestUtils.spyLambda;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dev.jacksonbailey.wheel.collections.modifiable.Bag;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BagsTest {

  @Mock
  Bag<Boolean> bag;

  static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(false, false, List.of(false, false), false, 2),
        Arguments.of(false, false, List.of(false, true), true, 2),
        Arguments.of(false, false, List.of(true, false), true, 2),
        Arguments.of(false, false, List.of(true, true), true, 2),
        Arguments.of(false, true, List.of(false, false), false, 2),
        Arguments.of(false, true, List.of(false, true), true, 2),
        Arguments.of(false, true, List.of(true, false), true, 1),
        Arguments.of(false, true, List.of(true, true), true, 1),
        Arguments.of(true, false, List.of(false, false), false, 2),
        Arguments.of(true, false, List.of(false, true), false, 2),
        Arguments.of(true, false, List.of(true, false), false, 2),
        Arguments.of(true, false, List.of(true, true), true, 2),
        Arguments.of(true, true, List.of(false, false), false, 1),
        Arguments.of(true, true, List.of(false, true), false, 1),
        Arguments.of(true, true, List.of(true, false), false, 2),
        Arguments.of(true, true, List.of(true, true), true, 2)
    );
  }

  @ParameterizedTest
  @MethodSource("provideArguments") // TODO Better name or messaging for this
  void andAcrossTruesNoShortCircuit(boolean useAnd, boolean shortCircuit,
      Iterable<Boolean> bagElements,
      boolean expectedResult, int expectedInvocations) {

    given(bag.iterator()).willReturn(bagElements.iterator());
    Predicate<Boolean> predicate = spyLambda(Predicate.class, Boolean::valueOf);

    var actualResult = Bags.applyAcrossAll(bag, useAnd, shortCircuit, predicate);

    assertEquals(expectedResult, actualResult);
    verify(predicate, times(expectedInvocations)).test(any());
  }

}
