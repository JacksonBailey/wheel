package dev.jacksonbailey.wheel.collections;

import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VChain;
import dev.jacksonbailey.wheel.collections.viewable.VPile;
import dev.jacksonbailey.wheel.collections.viewable.VSuccession;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class Bags {

  private Bags() {
  }

  // TODO Rename this ugly
  public static <E> boolean applyAcrossAll(@NotNull VBag<? extends E> bag, boolean useAnd,
      boolean shortCircuit, @NotNull Predicate<? super E> predicate) {
    var result = useAnd;
    for (E element : bag) {
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
