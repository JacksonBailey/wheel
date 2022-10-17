package dev.jacksonbailey.wheel.collections;

import dev.jacksonbailey.wheel.collections.modifiable.Bag;
import dev.jacksonbailey.wheel.collections.viewable.ViewableBag;
import java.util.function.Predicate;

public final class Bags {

  private Bags() {}

  // TODO Rename this ugly
  public static <E> boolean applyAcrossAll(ViewableBag<? extends E> bag, boolean useAnd, boolean shortCircuit,
      Predicate<? super E> predicate) {
    var result = useAnd;
    for (E element: bag) {
      if (useAnd) {
        result &= predicate.test(element);
      } else {
        result |= predicate.test(element);
      }
      if (shortCircuit && (result != useAnd)) {
        return result;
      }
    }
    return result;
  }
}
