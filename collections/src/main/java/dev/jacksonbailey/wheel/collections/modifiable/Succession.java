package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VSuccession;
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
 * First in, first out. Adds at tail/last and removes from head/first.
 */
public sealed interface Succession<E> extends VSuccession<E>, Bag<E> permits SuccessionLeaf, Chain {

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

  /**
   * Adds the element {@code e} to the tail of this.
   *
   * @param e the element to add
   * @return true if this was modified
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
   * Removes the first occurrence when iterating from head to tail.
   *
   * @param o the element to remove
   * @return true if this was modified
   */
  @Override
  @Contract("!null -> _; null -> false")
  default boolean remove(@Nullable Object o) {
    return removeFirstOccurrence(o);
  }

  @Override
  default boolean removeAll(@NotNull VBag<?> b) {
    return Bag.super.removeAll(b);
  }

  // TODO Check the defaults that call super


  /**
   * Removes the first occurrence when iterating from head to tail.
   *
   * @param o the element to remove
   * @return true if this was modified
   */
  @Contract("!null -> _; null -> false")
  boolean removeFirstOccurrence(@Nullable Object o);


  /**
   * Removes and returns the head of this.
   *
   * @return an optional possibly containing the head
   */
  @NotNull
  default Optional<E> removeHead() {
    var head = getHead();
    head.ifPresent(this::removeFirstOccurrence);
    return head;
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
  Succession<E> shallowCopy();

  @Override
  @NotNull
  default Iterator<E> iterator() {
    return walker();
  }

  @Override
  @NotNull
  Walker<E> walker();

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

  @Override
  @Contract("!null -> _; null -> false")
  boolean equals(@Nullable Object o);

  @Override
  int hashCode();
}
