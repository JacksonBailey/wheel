package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VPile;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Last in, first out. Adds at tail/last and removes from head/first.
 */
public interface Pile<E> extends VPile<E>, Bag<E> {

  @Override
  int size();

  @Override
  default boolean isEmpty() {
    return VPile.super.isEmpty();
  }

  @Override
  default boolean contains(@Nullable Object o) {
    return VPile.super.contains(o);
  }

  @Override
  default boolean containsAll(@NotNull VBag<?> b) {
    return VPile.super.containsAll(b);
  }

  @Override
  @NotNull
  Optional<E> getTail();

  /**
   * Adds the element {@code e} to the tail of this.
   *
   * @param e {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  @Override
  default boolean addAll(@NotNull VBag<? extends E> b) {
    return Bag.super.addAll(b);
  }

  /**
   * Adds the element {@code e} to the tail of this.
   *
   * @param e the element to add
   * @return true if this was modified
   */
  boolean addTail(@NotNull E e);

  /**
   * {@inheritDoc}
   * <p>
   * It is tempting to think this removes the last occurrence but that is not necessarily true.
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  @Contract("!null -> _; null -> false")
  boolean remove(@Nullable Object o);

  @Override
  default boolean removeAll(@NotNull VBag<?> b) {
    return Bag.super.removeAll(b);
  }

  // TODO Rename first/last occurrence methods?

  /**
   * Removes the first occurrence when iterating from tail to head.
   *
   * @param o the element to remove
   * @return true if this was modified
   */
  @Contract("!null -> _; null -> false")
  boolean removeLastOccurrence(@Nullable Object o);

  /**
   * Removes and returns the tail of this.
   *
   * @return an optional possibly containing the tail
   */
  @NotNull
  default Optional<E> removeTail() {
    var tail = getTail();
    tail.ifPresent(this::removeLastOccurrence);
    return tail;
  }

  @Override
  default boolean removeIf(@NotNull Predicate<? super E> filter) {
    return Bag.super.removeIf(filter);
  }

  @Override
  default boolean retainAll(@NotNull VBag<?> b) {
    return Bag.super.retainAll(b);
  }

  @Override
  default boolean clear() {
    return Bag.super.clear();
  }

  @Override
  @Contract("-> new")
  @NotNull
  Pile<E> shallowCopy();

  @Override
  @NotNull
  Iterator<E> iterator();

  @NotNull
  default Iterator<E> descendingIterator() {
    return descendingWalker();
  }

  @Override
  @NotNull
  Walker<E> walker();

  @NotNull
  Walker<E> descendingWalker();

  @Override
  default void forEach(Consumer<? super E> action) {
    VPile.super.forEach(action);
  }

  @Override
  @NotNull
  default Spliterator<E> spliterator() {
    return VPile.super.spliterator();
  }

  @Override
  @NotNull
  default Stream<E> stream() {
    return VPile.super.stream();
  }

  @Override
  @NotNull
  default Stream<E> parallelStream() {
    return VPile.super.parallelStream();
  }

  @Override
  @Contract("!null -> _; null -> false")
  boolean equals(@Nullable Object o);

  @Override
  int hashCode();

}
