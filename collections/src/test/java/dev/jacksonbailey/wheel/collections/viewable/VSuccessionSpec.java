package dev.jacksonbailey.wheel.collections.viewable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.List;
import org.jetbrains.annotations.Nullable;

class VSuccessionSpec {

  static <E> void invariants(VSuccession<E> succession) {
    assertAll(
        () -> VBagSpec.invariants(succession),
        () -> succession.contains(succession.getHead()),
        () -> succession.containsAll(new SimpleVBag<>(List.of(succession.getHead())))
    );
  }

  static <E> void contains(VSuccession<E> succession, @Nullable Object o, boolean expected) {
    assumeFalse(o == null && expected, "Bags cannot contain null");

    assertAll(
        () -> VBagSpec.contains(succession, o, expected)
    );
  }

  static <E> void containsAll(VSuccession<E> succession, VBag<E> b, boolean expected) {
    assertAll(
        () -> VBagSpec.containsAll(succession, b, expected)
    );
  }

  static <E> void shallowCopy(VSuccession<E> succession) {
    assertAll(
        () -> VBagSpec.shallowCopy(succession),
        () -> {
          var copy = succession.shallowCopy();
          assertAll(
              () -> assertEquals(succession.getHead(), copy.getHead()),
              () -> assertThat(copy).containsExactlyElementsOf(succession)
          );
        }
    );
  }

  static <E> void iterator(VSuccession<E> succession) {
    // TODO
    assertAll(
        () -> VBagSpec.iterator(succession)
    );
  }

  static <E> void walker(VSuccession<E> succession) {
    // TODO
    assertAll(
        () -> VBagSpec.walker(succession)
    );
  }

  static <E> void forEach(VSuccession<E> succession) {
    // TODO
    assertAll(
        () -> VBagSpec.forEach(succession)
    );
  }

  static <E> void spliterator(VSuccession<E> succession) {
    // TODO
    assertAll(
        () -> VBagSpec.spliterator(succession)
    );
  }

  static <E> void stream(VSuccession<E> succession) {
    // TODO
    assertAll(
        () -> VBagSpec.stream(succession)
    );
  }

  static <E> void parallelStream(VSuccession<E> succession) {
    // TODO
    assertAll(
        () -> VBagSpec.parallelStream(succession)
    );
  }

  // TODO equalsAndHashCode

}
