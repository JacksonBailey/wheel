package dev.jacksonbailey.wheel.collections.modifiable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Bags;
import dev.jacksonbailey.wheel.collections.viewable.VBag;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * In general these methods return true if the bag is modified.
 */
public sealed interface Bag<E> extends VBag<E> permits AbstractBag, Succession, Pile {

  boolean add(@NotNull E e);

  default boolean addAll(@NotNull Bag<? extends E> b) {
    return Bags.applyAcrossAll(requireNonNull(b), false, false, this::add);
  }

  boolean remove(@Nullable Object o);

  default boolean removeAll(@NotNull Bag<?> b) {
    return Bags.applyAcrossAll(requireNonNull(b), false, false, this::remove);
  }

  default boolean removeIf(@NotNull Predicate<? super E> filter) {
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
  @NotNull Bag<E> shallowCopy();

}
