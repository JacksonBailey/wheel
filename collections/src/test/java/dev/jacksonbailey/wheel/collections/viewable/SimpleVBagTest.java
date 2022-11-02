package dev.jacksonbailey.wheel.collections.viewable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SimpleVBagTest {

  @Test
  void emptyBag() {
    var bag = new SimpleVBag<>(Collections.emptyList());
    assertAll(
        () -> VBagSpec.invariants(bag),
        () -> VBagSpec.contains(bag, null, false),
        () -> VBagSpec.contains(bag, 0, false),
        () -> VBagSpec.contains(bag, 1, false),
        () -> VBagSpec.contains(bag, 2, false),
        () -> VBagSpec.contains(bag, 3, false),
        () -> VBagSpec.contains(bag, 4, false),
        () -> VBagSpec.contains(bag, "1", false),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(Collections.emptyList()), true),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(List.of(1)), false),
        () -> VBagSpec.shallowCopy(bag),
        () -> VBagSpec.iterator(bag),
        () -> VBagSpec.walker(bag),
        () -> VBagSpec.forEach(bag),
        () -> VBagSpec.spliterator(bag),
        () -> VBagSpec.stream(bag),
        () -> VBagSpec.parallelStream(bag),
        () -> VBagSpec.equalsAndHashCode(bag, null, false),
        () -> VBagSpec.equalsAndHashCode(bag, new SimpleVBag<>(Collections.emptyList()), true),
        () -> VBagSpec.equalsAndHashCode(bag, new SimpleVBag<>(List.of(1, 2, 3)), false)
    );
  }

  @Test
  void nonEmptyBag() {
    var bag = new SimpleVBag<>(List.of(1, 2, 3));
    assertAll(
        () -> VBagSpec.invariants(bag),
        () -> VBagSpec.contains(bag, null, false),
        () -> VBagSpec.contains(bag, 0, false),
        () -> VBagSpec.contains(bag, 1, true),
        () -> VBagSpec.contains(bag, 2, true),
        () -> VBagSpec.contains(bag, 3, true),
        () -> VBagSpec.contains(bag, 4, false),
        () -> VBagSpec.contains(bag, "1", false),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(Collections.emptyList()), true),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(List.of(1)), true),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(List.of(3, 2, 1)), true),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(List.of(1, 1)), true),
        () -> VBagSpec.containsAll(bag, new SimpleVBag<>(List.of(1, 4)), false),
        () -> VBagSpec.shallowCopy(bag),
        () -> VBagSpec.iterator(bag),
        () -> VBagSpec.walker(bag),
        () -> VBagSpec.forEach(bag),
        () -> VBagSpec.spliterator(bag),
        () -> VBagSpec.stream(bag),
        () -> VBagSpec.parallelStream(bag),
        () -> VBagSpec.equalsAndHashCode(bag, null, false),
        () -> VBagSpec.equalsAndHashCode(bag, new SimpleVBag<>(Collections.emptyList()), false),
        () -> VBagSpec.equalsAndHashCode(bag, new SimpleVBag<>(List.of(3, 2, 1)), true)
    );
  }

  @Test
  void noNullsAllowed() {
    var list = new ArrayList<>();
    list.add(null);
    assertThrows(NullPointerException.class, () -> new SimpleVBag<>(list));
  }

}
