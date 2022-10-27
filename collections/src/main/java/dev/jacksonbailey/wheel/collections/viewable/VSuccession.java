package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a viewable queue of elements using first in, first out semantics.
 *
 * @param <E> the type of elements in the succession
 */
public interface VSuccession<E> extends VBag<E> {

  @Override
  int size();

  @Override
  default boolean isEmpty() {
    return VBag.super.isEmpty();
  }

  @Override
  default boolean contains(@Nullable Object o) {
    return VBag.super.contains(o);
  }

  @Override
  default boolean containsAll(@NotNull VBag<?> b) {
    return VBag.super.containsAll(b);
  }

  /**
   * Returns an {@code Optional} of the head element in the succession. {@code Optional.empty()} if
   * this {@code isEmpty()}. The head can be thought of as the front or the first element.
   *
   * @return an {@code Optional} of the head element in the succession
   */
  @NotNull
  Optional<E> getHead();

  @Override
  @Contract("-> new")
  @NotNull
  VSuccession<E> shallowCopy();

  /**
   * Returns an iterator over the elements of this succession. The elements are ordered from head to
   * tail.
   *
   * @return {@inheritDoc}
   */
  @Override
  @NotNull
  default Iterator<E> iterator() {
    return walker();
  }

  /**
   * Returns a walker over the elements of this succession. The elements are ordered from head to
   * tail.
   *
   * @return {@inheritDoc}
   */
  @Override
  @NotNull
  Walker<E> walker();

  @Override
  default void forEach(Consumer<? super E> action) {
    VBag.super.forEach(action);
  }

  @Override
  @NotNull
  default Spliterator<E> spliterator() {
    return VBag.super.spliterator();
  }

  @Override
  @NotNull
  default Stream<E> stream() {
    return VBag.super.stream();
  }

  @Override
  @NotNull
  default Stream<E> parallelStream() {
    return VBag.super.parallelStream();
  }

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

  @Override
  int hashCode();

}
