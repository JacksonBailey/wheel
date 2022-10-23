package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Chain;
import java.util.Iterator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a viewable double-ended queue of elements.
 *
 * @param <E> the type of elements in the chain
 */
public sealed interface VChain<E> extends VSuccession<E>, VPile<E> permits VChainLeaf, Chain {

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  @Contract("-> new")
  @NotNull VChain<E> shallowCopy();

  /**
   * Returns an iterator over the elements of this as a succession. The elements are ordered from
   * head to tail.
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

  // TODO Update last bullet point when needed
  /**
   * Compares the specified object with this for equality.
   * <p>
   * True if and only if
   * <ul>
   *   <li>{@code o} and this contain the same number of elements</li>
   *   <li>For each element in {@code o} there is a corresponding element in this in the same position</li>
   *   <li>{@code o} is a {@code VChain} and NOT a ...</li>
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
