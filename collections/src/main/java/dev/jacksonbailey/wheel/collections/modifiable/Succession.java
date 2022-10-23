package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VSuccession;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * First in, first out. Adds at tail/last and removes from head/first.
 */
public sealed interface Succession<E> extends VSuccession<E>, Bag<E> permits SuccessionLeaf, Chain {

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  boolean addTail(@NotNull E e);

  // Removes from head
  @Override
  @Contract("!null -> _; null -> false")
  default boolean remove(@Nullable Object o) {
    return removeFirstOccurrence(o);
  }

  @Contract("!null -> _; null -> false")
  boolean removeFirstOccurrence(@Nullable Object o);

  @NotNull Optional<E> removeHead();

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  @Contract("-> new")
  @NotNull Succession<E> shallowCopy();

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
