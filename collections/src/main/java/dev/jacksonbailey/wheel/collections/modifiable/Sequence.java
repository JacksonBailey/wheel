package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VSequence;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Sequence<E> extends VSequence<E>, Chain<E> {

  @Override
  int size();

  @Override
  default boolean isEmpty() {
    return VSequence.super.isEmpty();
  }

  @Override
  default boolean contains(@Nullable Object o) {
    return VSequence.super.contains(o);
  }

  @Override
  default boolean containsAll(@NotNull VBag<?> b) {
    return VSequence.super.containsAll(b);
  }

  @Override
  @NotNull
  Optional<E> getHead();

  @Override
  @NotNull
  Optional<E> getTail();

  @Override
  default boolean add(@NotNull E e) {
    return Chain.super.add(e);
  }

  @Override
  default boolean addAll(@NotNull VBag<? extends E> b) {
    return Chain.super.addAll(b);
  }

  @Override
  boolean addHead(@NotNull E e);

  @Override
  boolean addTail(@NotNull E e);

  @Override
  boolean remove(@Nullable Object o);

  @Override
  default boolean removeAll(@NotNull VBag<?> b) {
    return Chain.super.removeAll(b);
  }

  @Override
  boolean removeFirstOccurrence(@Nullable Object o);

  @Override
  boolean removeLastOccurrence(@Nullable Object o);

  @Override
  @NotNull
  default Optional<E> removeHead() {
    return Chain.super.removeHead();
  }

  @Override
  @NotNull
  default Optional<E> removeTail() {
    return Chain.super.removeTail();
  }

  @Override
  default boolean removeIf(@NotNull Predicate<? super E> filter) {
    return Chain.super.removeIf(filter);
  }

  @Override
  default boolean retainAll(@NotNull VBag<?> b) {
    return Chain.super.retainAll(b);
  }

  @Override
  default boolean clear() {
    return Chain.super.clear();
  }

  @Override
  @NotNull
  Sequence<E> shallowCopy();

  @Override
  @NotNull
  default Iterator<E> iterator() {
    return VSequence.super.iterator();
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
    VSequence.super.forEach(action);
  }

  @Override
  @NotNull
  default Spliterator<E> spliterator() {
    return VSequence.super.spliterator();
  }

  // TODO Make sure all methods that can be marked default due to a super actually are

  @Override
  @NotNull
  default Stream<E> stream() {
    return VSequence.super.stream();
  }

  @Override
  @NotNull
  default Stream<E> parallelStream() {
    return VSequence.super.parallelStream();
  }

  @Override
  boolean equals(@Nullable Object o);

  @Override
  int hashCode();

}
