package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.VPile;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Last in, first out. Adds at tail/last and removes from head/first.
 */
public sealed interface Pile<E> extends VPile<E>, Bag<E> permits PileLeaf, Chain {

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  boolean addTail(@NotNull E e);

  // Removes from somewhere, *wink*
  @Override
  @Contract("!null -> _; null -> false")
  default boolean remove(@Nullable Object o) {
    return removeLastOccurrence(o);
  }

  @Contract("!null -> _; null -> false")
  boolean removeLastOccurrence(@Nullable Object o);

  Optional<E> removeTail();


  @Override
  @Contract("-> new")
  @NotNull Pile<E> shallowCopy();

}
