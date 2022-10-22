package dev.jacksonbailey.wheel.collections;

import static dev.jacksonbailey.wheel.collections.TestUtils.spyLambda;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dev.jacksonbailey.wheel.collections.modifiable.AbstractBag;
import dev.jacksonbailey.wheel.collections.viewable.AbstractVBag;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class BagsTest {

  static Stream<Arguments> provideArgumentsForApplyAcrossAll() {
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

  // TODO Better name or messaging for this
  @ParameterizedTest
  @MethodSource("provideArgumentsForApplyAcrossAll")
  @SuppressWarnings("unchecked")
  void applyAcrossAllTest(boolean useAnd, boolean shortCircuit,
      Iterable<Boolean> bagElements,
      boolean expectedResult, int expectedInvocations) {

    AbstractBag<Boolean> bag = mock(AbstractBag.class, Mockito.CALLS_REAL_METHODS);
    given(bag.iterator()).willReturn(bagElements.iterator());
    Predicate<Boolean> predicate = spyLambda(Predicate.class, Boolean::valueOf);

    var actualResult = Bags.applyAcrossAll(bag, useAnd, shortCircuit, predicate);

    assertEquals(expectedResult, actualResult);
    verify(predicate, times(expectedInvocations)).test(any());
  }

  static Stream<Arguments> provideArgumentsForContainsExactlyAll() {
    return Stream.of(
        Arguments.of(List.of(1), List.of(1), true),
        Arguments.of(List.of(1), List.of(1, 2), false),
        Arguments.of(List.of(1, 2), List.of(1), false),
        Arguments.of(List.of(2, 1), List.of(1, 2), true),
        Arguments.of(List.of(1, 1, 2), List.of(1, 2, 2), false),
        Arguments.of(List.of(1, 2, 2), List.of(1, 1, 2), false)
    );
  }

  @ParameterizedTest
  @MethodSource("provideArgumentsForContainsExactlyAll")
  @SuppressWarnings("unchecked")
  void containsExactlyAllTest(Collection<Integer> bag1Items, Collection<Integer> bag2Items,
      boolean expectedResult) {

    AbstractVBag<Integer> bag1 = mock(AbstractVBag.class, Mockito.CALLS_REAL_METHODS);
    given(bag1.size()).willReturn(bag1Items.size());
    given(bag1.iterator()).willReturn(bag1Items.iterator());

    AbstractVBag<Integer> bag2 = mock(AbstractVBag.class, Mockito.CALLS_REAL_METHODS);
    given(bag2.size()).willReturn(bag2Items.size());
    given(bag2.iterator()).willReturn(bag2Items.iterator());

    assertEquals(expectedResult, Bags.containsExactlyAll(bag1, bag2));
  }

}
