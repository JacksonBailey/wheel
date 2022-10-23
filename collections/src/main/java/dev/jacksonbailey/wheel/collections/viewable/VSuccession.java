package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Succession;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a viewable queue of elements using first in, first out semantics.
 *
 * @param <E> the type of elements in the succession
 */
public sealed interface VSuccession<E> extends VBag<E> permits VSuccessionLeaf, VChain, Succession {

  /**
   * Returns an {@code Optional} of the head element in the succession. {@code Optional.empty()} if
   * this {@code isEmpty()}.
   *
   * @return an {@code Optional} of the head element in the succession
   */
  @NotNull Optional<E> getFirst();

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  @NotNull VSuccession<E> shallowCopy();

  /**
   * Returns an iterator over the elements of this succession. The elements are ordered from head to
   * tail.
   *
   * @return {@inheritDoc}
   */
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }

  /**
   * See {@link #iterator()} for details on the order.
   *
   * @return {@inheritDoc}
   */
  @Override
  @NotNull Walker<E> walker();

  /**
   * Compares the specified object with this for equality.
   * <p>
   * True if and only if
   * <ul>
   *   <li>{@code o} and this contain the same number of elements</li>
   *   <li>For each element in {@code o} there is a corresponding element in this in the same position</li>
   *   <li>{@code o} is a {@code VSuccession} and NOT a {@code VPile}</li>
   * </ul>
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  boolean equals(Object o);

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  int hashCode();

}
