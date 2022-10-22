package dev.jacksonbailey.wheel.collections;

import dev.jacksonbailey.wheel.collections.viewable.VBag;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class Bags {

  private Bags() {
  }

  // TODO Rename this ugly
  public static <E> boolean applyAcrossAll(@NotNull Iterable<? extends E> iterable, boolean useAnd,
      boolean shortCircuit, @NotNull Predicate<? super E> predicate) {
    var result = useAnd;
    for (E element : iterable) {
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

  // TODO Make shallow copy?
  public static boolean containsExactlyAll(@NotNull VBag<?> bag1, @NotNull VBag<?> bag2) {

    if (bag1.size() != bag2.size()) {
      return false;
    }

    var list1 = new ArrayList<>();
    bag1.forEach(list1::add);

    var list2 = new ArrayList<>();
    bag2.forEach(list2::add);

    for (Object o : list1) {
      if (!list2.remove(o)) {
        return false;
      }
    }
    return true;
  }

  public static boolean containsInOrder(@NotNull Walker<?> walker1, @NotNull Walker<?> walker2) {
    while (walker1.hasNext() || walker2.hasNext()) {
      if (!Objects.equals(walker1.maybeNext(), walker2.maybeNext())) {
        return false;
      }
    }
    return true;
  }

  public static int hashingInOrder(@NotNull Walker<?> walker) {
    int hash = 1;
    while (walker.hasNext()) {
      hash = 31 * hash + Objects.hashCode(walker.next());
    }
    return hash;
  }


}
