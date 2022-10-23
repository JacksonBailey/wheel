package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VPile;
import java.util.Iterator;
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

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  @Contract("-> new")
  @NotNull Pile<E> shallowCopy();

  /**
   * {@inheritDoc}
   * <p>
   * It is tempting to think that this iterates from head to tail. That is not necessarily true. It
   * should be considered random.
   *
   * @return {@inheritDoc}
   */
  @Override
  @NotNull Iterator<E> iterator();

  /**
   * Returns an iterator over the elements of this pile. The elements are ordered from tail to head.
   *
   * @return the iterator
   */
  default @NotNull Iterator<E> descendingIterator() {
    return descendingWalker();
  }

  /**
   * See {@link #iterator()} for details on the order.
   * <p>
   * It is tempting to think that this walks from head to tail. That is not necessarily true.
   *
   * @return {@inheritDoc}
   */
  @Override
  @NotNull Walker<E> walker();

  /**
   * See {@link #descendingIterator()} for details on the order.
   *
   * @return the walker
   */
  @NotNull Walker<E> descendingWalker();

  /**
   * Compares the specified object with this for equality.
   * <p>
   * True if and only if
   * <ul>
   *   <li>{@code o} and this contain the same number of elements</li>
   *   <li>For each element in {@code o} there is a corresponding element in this in the same position</li>
   *   <li>{@code o} is a {@code VPile} and NOT a {@code VSuccession}</li>
   * </ul>
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  @Contract("!null -> _; null -> false")
  boolean equals(@Nullable Object o);

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  int hashCode();

}
