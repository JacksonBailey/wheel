package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VChain;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Chain<E> extends VChain<E>, Succession<E>, Pile<E> {

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
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  @Override
  default boolean addAll(@NotNull VBag<? extends E> b) {
    return Succession.super.addAll(b);
  }

  /**
   * Adds the element {@code e} to the head of this.
   *
   * @param e the element to add
   * @return true if this was modified
   */
  boolean addHead(@NotNull E e);

  @Override
  boolean addTail(@NotNull E e);

  @Override
  @Contract("!null -> _; null -> false")
  boolean remove(@Nullable Object o);

  @Override
  default boolean removeAll(@NotNull VBag<?> b) {
    return Succession.super.removeAll(b);
  }

  @Override
  boolean removeFirstOccurrence(@Nullable Object o);

  @Override
  boolean removeLastOccurrence(@Nullable Object o);

  @Override
  @NotNull
  default Optional<E> removeHead() {
    return Succession.super.removeHead();
  }

  @Override
  @NotNull
  default Optional<E> removeTail() {
    return Pile.super.removeTail();
  }

  @Override
  default boolean removeIf(@NotNull Predicate<? super E> filter) {
    return Succession.super.removeIf(filter);
  }

  @Override
  default boolean retainAll(@NotNull VBag<?> b) {
    return Succession.super.retainAll(b);
  }

  @Override
  default boolean clear() {
    return Succession.super.clear();
  }

  @Override
  @Contract("-> new")
  @NotNull
  Chain<E> shallowCopy();

  @Override
  @NotNull
  default Iterator<E> iterator() {
    return walker();
  }

  @Override
  @NotNull
  default Iterator<E> descendingIterator() {
    return descendingWalker();
  }

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

  @Override
  @Contract("!null -> _; null -> false")
  boolean equals(@Nullable Object o);

  @Override
  int hashCode();

}
