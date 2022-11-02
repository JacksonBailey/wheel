package dev.jacksonbailey.wheel.collections.viewable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.util.Collections;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

class VBagSpec {

  static void invariants(VBag<?> bag) {
    assertAll(
        () -> assertThat(bag.size()).isGreaterThanOrEqualTo(0),
        () -> {
          if (bag.isEmpty()) {
            assertThat(bag.size()).isEqualTo(0);
          } else {
            assertThat(bag.size()).isGreaterThan(0);
          }
        },
        () -> assertTrue(bag.containsAll(bag)),
        () -> {
          for (Object o : bag) {
            assertTrue(bag.contains(o));
          }
        },
        () -> assertTrue(bag.containsAll(new SimpleVBag<>(Collections.emptyList()))),
        () -> assertNotEquals(null, bag)
    );
  }

  static void contains(VBag<?> bag, @Nullable Object o, boolean expected) {
    assumeFalse(o == null && expected, "Bags cannot contain null");

    assertAll(
        () -> {
          if (expected) {
            assertThat(bag.size()).isGreaterThan(0);
          }
        },
        () -> {
          if (o == null) {
            assertFalse(bag.contains(null));
          } else {
            assertEquals(expected, bag.contains(o));
          }
        }
    );

  }

  static void containsAll(VBag<?> bag, VBag<?> b, boolean expected) {
    assertAll(
        () -> assertEquals(expected, bag.containsAll(b)),
        () -> {
          boolean notFoundMissingElement = true;
          for (Object o : b) {
            notFoundMissingElement &= bag.contains(o);
          }
          assertEquals(expected, notFoundMissingElement);
        }
    );
  }

  static void shallowCopy(VBag<?> bag) {
    var copy = bag.shallowCopy();
    assertAll(
        () -> assertEquals(bag, copy),
        () -> assertEquals(bag.hashCode(), copy.hashCode())
    );
  }

  static void iterator(VBag<?> bag) {
    // TODO
  }

  static void walker(VBag<?> bag) {
    // TODO
  }

  static void forEach(VBag<?> bag) {
    // TODO
  }

  static void spliterator(VBag<?> bag) {
    // TODO
  }

  static void stream(VBag<?> bag) {
    // TODO
  }

  static void parallelStream(VBag<?> bag) {
    // TODO
  }

  static void equalsAndHashCode(@Nullable VBag<?> bag1, @Nullable VBag<?> bag2, boolean expected) {
    assertAll(
        () -> {
          assertEquals(expected, Objects.equals(bag1, bag2));
          assumingThat(expected, () -> assertEquals(Objects.hashCode(bag1), Objects.hashCode(bag2)));
        },
        () -> {
          assertEquals(expected, Objects.equals(bag2, bag1));
          assumingThat(expected, () -> assertEquals(Objects.hashCode(bag2), Objects.hashCode(bag1)));
        }
    );
  }
}
