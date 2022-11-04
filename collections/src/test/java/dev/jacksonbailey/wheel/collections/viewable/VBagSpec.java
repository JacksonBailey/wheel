package dev.jacksonbailey.wheel.collections.viewable;

import static dev.jacksonbailey.wheel.collections.TestUtils.toIterable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.util.Collections;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

class VBagSpec {

  static <E> void invariants(VBag<E> bag) {
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

  static <E> void contains(VBag<E> bag, @Nullable Object o, boolean expected) {
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

  static <E> void containsAll(VBag<E> bag, VBag<?> b, boolean expected) {
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

  static <E> void shallowCopy(VBag<E> bag) {
    var copy = bag.shallowCopy();
    assertAll(
        () -> assertThat(copy).containsExactlyInAnyOrderElementsOf(bag),
        () -> assertEquals(bag, copy),
        () -> assertEquals(bag.hashCode(), copy.hashCode())
    );
  }

  static <E> void iterator(VBag<E> bag) {
    assertAll(
        () -> assumingThat(!bag.isEmpty(), () -> assertThat(bag.iterator()).hasNext()),
        () -> {
          var it = bag.iterator();
          for (int i = 0; i < bag.size(); i++) {
            assertThat(it.next()).isNotNull(); // No nulls allowed
          }
          assertThat(it).isExhausted();
        },
        () -> assertThat(bag.iterator()).toIterable().size().isEqualTo(bag.size()),
        () -> assertThat(bag.iterator()).toIterable().allMatch(bag::contains),
        () -> assertThat(bag.iterator()).toIterable()
                                        .containsExactlyInAnyOrderElementsOf(
                                            toIterable(bag.iterator())
                                        )
    );
  }

  static <E> void walker(VBag<E> bag) {
    assertAll(
        () -> assumingThat(!bag.isEmpty(), () -> assertThat(bag.walker()).hasNext()),
        () -> assumingThat(!bag.isEmpty(), () -> assertThat(bag.walker().maybeNext()).isPresent()),
        () -> {
          var walker = bag.walker();
          for (int i = 0; i < bag.size(); i++) {
            assertThat(walker.next()).isNotNull(); // No nulls allowed
          }
          assertThat(walker).isExhausted();
        },
        () -> {
          var walker = bag.walker();
          var next = walker.maybeNext();
          while (next.isPresent()) {
            next = walker.maybeNext();
          }
          assertThat(walker).isExhausted();
        },
        () -> assertThat(bag.walker()).toIterable().size().isEqualTo(bag.size()),
        () -> assertThat(bag.walker()).toIterable().allMatch(bag::contains),
        () -> assertThat(bag.walker()).toIterable()
                                      .containsExactlyInAnyOrderElementsOf(
                                          toIterable(bag.walker())
                                      )
    );
  }

  static <E> void forEach(VBag<E> bag) {
    // TODO
  }

  static <E> void spliterator(VBag<E> bag) {
    // TODO
  }

  static <E> void stream(VBag<E> bag) {
    // TODO
  }

  static <E> void parallelStream(VBag<E> bag) {
    // TODO
  }

  static <E> void equalsAndHashCode(@Nullable VBag<E> bag, @Nullable VBag<?> b, boolean expected) {
    assertAll(
        () -> {
          assertEquals(expected, Objects.equals(bag, b));
          assumingThat(expected, () -> assertEquals(Objects.hashCode(bag), Objects.hashCode(b)));
        },
        () -> {
          assertEquals(expected, Objects.equals(b, bag));
          assumingThat(expected, () -> assertEquals(Objects.hashCode(b), Objects.hashCode(bag)));
        }
    );
  }
}
