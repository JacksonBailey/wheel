package dev.jacksonbailey.wheel.collections.modifiable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SimpleBagTest {

  Bag<Integer> bag = new SimpleBag<>();

  @Test
  void addAll() {
    assertTrue(bag.addAll(new SimpleBag<>(List.of(1, 2, 3))));
    assertTrue(bag.containsAll(new SimpleBag<>(List.of(3, 2, 1))));
    assertEquals(3, bag.size());
  }

  @Test
  void removeAll() {
    var otherBag = new SimpleBag<>(List.of(1, 2, 3));
    assertTrue(bag.addAll(otherBag));
    assertTrue(bag.removeAll(otherBag));
    assertTrue(bag.isEmpty());
    assertEquals(0, bag.size());
  }

  @Test
  void removeIf() {
    assertTrue(bag.addAll(new SimpleBag<>(List.of(1, 2, 3))));
    assertTrue(bag.removeIf(i -> i % 2 == 0));
    assertFalse(bag.contains(2));
    assertTrue(bag.containsAll(new SimpleBag<>(List.of(3, 1))));
    assertEquals(2, bag.size());
  }

  @Test
  void retainAll() {
    assertTrue(bag.addAll(new SimpleBag<>(List.of(1, 2, 3))));
    assertTrue(bag.retainAll(new SimpleBag<>(List.of(1, 3))));
    assertTrue(bag.containsAll(new SimpleBag<>(List.of(3, 1))));
    assertEquals(2, bag.size());
  }

}
