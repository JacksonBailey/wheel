package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VChain;
import java.util.Iterator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface Chain<E> extends VChain<E>, Succession<E>, Pile<E> permits ChainLeaf {

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  boolean addHead(@NotNull E e);

  // Removes from head
  @Override
  @Contract("!null -> _; null -> false")
  boolean remove(@Nullable Object o);

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  @Contract("-> new")
  @NotNull Chain<E> shallowCopy();

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
