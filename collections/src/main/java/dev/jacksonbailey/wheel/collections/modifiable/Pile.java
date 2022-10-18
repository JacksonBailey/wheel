package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.ViewablePile;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Last in, first out. Adds at tail/last and removes from head/first.
 */
public interface Pile<E> extends ViewablePile<E>, Bag<E> {

  // TODO push and pop?

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addLast(e);
  }

  boolean addLast(E e);

  // Removes from somewhere, *wink*
  @Override
  default boolean remove(Object o) {
    return removeLastOccurrence(o);
  }

  Optional<E> removeLast();

  boolean removeLastOccurrence(Object o);


  @Override
  @NotNull
  Pile<E> shallowCopy();

}
