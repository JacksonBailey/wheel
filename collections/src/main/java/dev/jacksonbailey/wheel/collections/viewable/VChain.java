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
 * Represents a viewable double-ended queue of elements.
 *
 * @param <E> the type of elements in the chain
 */
public interface VChain<E> extends VSuccession<E>, VPile<E> {

  @Override
  int size();

  @Override
  default boolean isEmpty() {
    return VSuccession.super.isEmpty();
  }

  @Override
  default boolean contains(@Nullable Object o) {
    return VSuccession.super.contains(o);
  }

  @Override
  default boolean containsAll(@NotNull VBag<?> b) {
    return VSuccession.super.containsAll(b);
  }

  @Override
  @NotNull Optional<E> getHead();

  @Override
  @NotNull Optional<E> getTail();

  @Override
  @Contract("-> new")
  @NotNull
  VChain<E> shallowCopy();

  @Override
  @NotNull
  default Iterator<E> iterator() {
    return walker();
  }

  @Override
  @NotNull
  Iterator<E> descendingIterator();

  @Override
  @NotNull
  Walker<E> walker();

  @Override
  @NotNull
  Walker<E> descendingWalker();

  @Override
  default void forEach(Consumer<? super E> action) {
    VSuccession.super.forEach(action);
  }

  @Override
  @NotNull
  default Spliterator<E> spliterator() {
    return VSuccession.super.spliterator();
  }

  @Override
  @NotNull
  default Stream<E> stream() {
    return VSuccession.super.stream();
  }

  @Override
  @NotNull
  default Stream<E> parallelStream() {
    return VSuccession.super.parallelStream();
  }

  /**
   * Compares the specified object with this for equality.
   * <p>
   * True if and only if
   * <ul>
   *   <li>{@code o} and this contain the same number of elements</li>
   *   <li>For each element in {@code o} there is a corresponding element in this in the same position</li>
   *   <li>{@code o} is a {@code VChain} and NOT a {@code VSequence}</li>
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
