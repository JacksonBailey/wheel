package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Pile;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a viewable stack of elements using last in, first out semantics.
 *
 * @param <E> the type of elements in the pile
 */
public sealed interface VPile<E> extends VBag<E> permits VPileLeaf, VChain, Pile {

  /**
   * Returns an {@code Optional} of the tail element in the pile. {@code Optional.empty()} if this
   * {@code isEmpty()}. The tail can be thought of as the end or the last element.
   *
   * @return an {@code Optional} of the tail element in the pile
   */
  @NotNull Optional<E> getTail();

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  @Contract("-> new")
  @NotNull VPile<E> shallowCopy();

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
