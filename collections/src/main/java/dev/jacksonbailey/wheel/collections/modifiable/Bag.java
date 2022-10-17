package dev.jacksonbailey.wheel.collections.modifiable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Bags;
import dev.jacksonbailey.wheel.collections.viewable.ViewableBag;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/**
 * In general these methods return true if the bag is modified.
 */
public interface Bag<E> extends ViewableBag<E> {

  boolean add(E e);
  default boolean addAll(@NotNull Bag<? extends E> b) {
    return Bags.applyAcrossAll(requireNonNull(b), false, false, this::add);
  }

  boolean remove(Object o);
  default boolean removeAll(@NotNull Bag<?> b) {
    return Bags.applyAcrossAll(requireNonNull(b), false, false, this::remove);
  }

  default boolean removeIf(@NotNull Predicate<? super E> filter) throws UnsupportedOperationException {
    requireNonNull(filter);
    var copy = shallowCopy();
    return Bags.applyAcrossAll(copy, false, false, e -> filter.test(e) && remove(e));
  }

  default boolean retainAll(@NotNull Bag<?> b) {
    requireNonNull(b);
    var copy = shallowCopy();
    return Bags.applyAcrossAll(copy, false, false,
        e -> removeIf(Predicate.not(b::contains)));
  }

  default void clear() {
    removeAll(shallowCopy());
  }

  @Override
  Bag<E> shallowCopy();

}
