package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VPile;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Last in, first out. Adds at tail/last and removes from head/first.
 */
public interface Pile<E> extends VPile<E>, Bag<E> {

  /**
   * Adds the element {@code e} to the tail of this.
   *
   * @param e {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  // TODO Should addAll be defined in terms of add? If so remove
  @Override
  default boolean addAll(@NotNull VBag<? extends E> b) {
    return Bag.super.addAll(b);
  }

  /**
   * Adds the element {@code e} to the tail of this.
   *
   * @param e the element to add
   * @return true if this was modified
   */
  boolean addTail(@NotNull E e);

  /**
   * {@inheritDoc}
   * <p>
   * It is tempting to think this removes the last occurrence but that is not necessarily true.
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  @Contract("!null -> _; null -> false")
  boolean remove(@Nullable Object o);

  // TODO Should removeAll be defined in terms of remove? If so remove this
  @Override
  default boolean removeAll(@NotNull VBag<?> b) {
    return Bag.super.removeAll(b);
  }

  // TODO Rename first/last occurrence methods?

  /**
   * Removes the first occurrence when iterating from tail to head.
   *
   * @param o the element to remove
   * @return true if this was modified
   */
  @Contract("!null -> _; null -> false")
  boolean removeLastOccurrence(@Nullable Object o);

  /**
   * Removes and returns the tail of this.
   *
   * @return an optional possibly containing the tail
   */
  @NotNull
  default Optional<E> removeTail() {
    var tail = getTail();
    tail.ifPresent(this::removeLastOccurrence);
    return tail;
  }

  @Override
  default boolean retainAll(@NotNull VBag<?> b) {
    return Bag.super.retainAll(b);
  }


  @Override
  @Contract("-> new")
  @NotNull
  Pile<E> shallowCopy();

}
