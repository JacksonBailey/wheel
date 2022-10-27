package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VSuccession;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * First in, first out. Adds at tail/last and removes from head/first.
 */
public interface Succession<E> extends VSuccession<E>, Bag<E> {

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

  // TODO Is this (1, 1).retainAll((1)) -> (1) or ()?
  @Override
  default boolean retainAll(@NotNull VBag<?> b) {
    return Bag.super.retainAll(b);
  }

  @Override
  @Contract("-> new")
  @NotNull
  Succession<E> shallowCopy();
}
