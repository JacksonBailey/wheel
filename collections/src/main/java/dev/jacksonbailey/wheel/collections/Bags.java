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

  /*
   * TODO I keep getting this nagging feeling that having VBag specify equals is problematic because
   * it violates behavioral subtyping. I don't think there's a way to correctly have reflexivity
   * and subtyping. Effective Java says as much but I thought that was only for "record" style
   * classes.
   */

  /**
   * @apiNote This takes a {@code Walker} as opposed to a {@code VBag} so that the caller can
   * specify the order to hash in. As an example, {@code VSuccession} will want to be hashed in a
   * different order than {@code VPile}.
   *
   * @implNote {@link java.util.stream.IntStream#reduce IntStream.reduce} is not specified to be
   * sequential, although it appears the implementation is. It's best to not rely on unspecified
   * behaviors. This will just use a non-stream approach rather than doing something funky with an
   * {@link java.util.concurrent.atomic.AtomicInteger AtomicInteger}.
   */
  public static int productHashingInOrder(@NotNull Walker<?> walker) {
    int hash = 1;
    while (walker.hasNext()) {
      hash = 31 * hash + walker.next().hashCode();
    }
    return hash;
  }

  public static int sumHashing(VBag<?> bag) {
    return bag.stream()
              .mapToInt(Object::hashCode)
              .sum();
  }


}
