package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Sequence;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface VSequence<E> extends VChain<E> permits VSequenceLeaf, Sequence {

  // TODO Do I *actually* need all method stubs on all interfaces...?

  @Override
  int size();

  @Override
  default boolean isEmpty() {
    return VChain.super.isEmpty();
  }

  @Override
  default boolean contains(@Nullable Object o) {
    return VChain.super.contains(o);
  }

  @Override
  default boolean containsAll(@NotNull VBag<?> b) {
    return VChain.super.containsAll(b);
  }

  @Override
  @NotNull Optional<E> getHead();

  @Override
  @NotNull Optional<E> getTail();

  @Override
  @NotNull VSequence<E> shallowCopy();

  @Override
  @NotNull
  default Iterator<E> iterator() {
    return VChain.super.iterator();
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
    VChain.super.forEach(action);
  }

  @Override
  @NotNull
  default Spliterator<E> spliterator() {
    return VChain.super.spliterator();
  }

  @Override
  @NotNull
  default Stream<E> stream() {
    return VChain.super.stream();
  }

  @Override
  @NotNull
  default Stream<E> parallelStream() {
    return VChain.super.parallelStream();
  }

  // TODO Add seq methods

  // TODO Update last bullet when needed
  /**
   * Compares the specified object with this for equality.
   * <p>
   * True if and only if
   * <ul>
   *   <li>{@code o} and this contain the same number of elements</li>
   *   <li>For each element in {@code o} there is a corresponding element in this in the same position</li>
   *   <li>{@code o} is a {@code VSequence} and NOT a {@code ...}</li>
   * </ul>
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  boolean equals(@Nullable Object o);

  @Override
  int hashCode();

}
