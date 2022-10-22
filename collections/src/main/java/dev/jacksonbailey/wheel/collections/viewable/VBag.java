package dev.jacksonbailey.wheel.collections.viewable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Bags;
import dev.jacksonbailey.wheel.collections.Walkable;
import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Bag;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a viewable group of elements.
 * <p>
 * There may or may not be multiples allowed. They may be sorted or not. All you can rely on is that
 * no element is {@code null}. No {@code null}s are allowed in any bag.
 * <p>
 * This cannot be directly modified but is not necessarily unmodifiable. To get a copy that won't be
 * modified by something else use {@link #shallowCopy()} (and then don't pass it to anywhere).
 *
 * @param <E> the type of elements in the bag
 */
public sealed interface VBag<E> extends Walkable<E> permits AbstractVBag, VSuccession, VPile, Bag {

  /**
   * Returns the number of elements in this bag.
   *
   * @return the number of elements in this bag
   */
  int size();

  /**
   * Returns whether this bag contains no elements.
   *
   * @return true if this bag contains no elements.
   */
  default boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Returns whether this bag contains an element {@code o} such that any element {@code e} makes
   * {@code Objects.equals(e, o)} true.
   *
   * @param o object to test the presence of
   * @return true if this bag contains {@code o}
   */
  default boolean contains(@Nullable Object o) {
    if (o == null) {
      return false;
    }
    return Bags.applyAcrossAll(this, false, true, e -> Objects.equals(e, o));
  }

  /**
   * Returns whether this bag contains all the elements in the bag {@code b} according to
   * {@link #contains(Object)} for each. Where {@code contains} is overridden it uses the logic of
   * that instead.
   *
   * @param b bag containing the objects to test the presence of
   * @return true if this bag contains all the elements in {@code b}
   */
  default boolean containsAll(@NotNull VBag<?> b) {
    return Bags.applyAcrossAll(requireNonNull(b), true, true, this::contains);
  }

  /**
   * Returns a shallow copy of this. That is to say it makes a new object that all the same elements
   * but the elements themselves are not new. Useful to get a copy that won't be modified by others
   * unexpectedly.
   *
   * @return the copy
   */
  @NotNull VBag<E> shallowCopy();

  /**
   * Returns an iterator over the elements of this bag. There's no guarantee about the order the
   * elements are returned in. Each element will be returned once.
   *
   * @return the iterator
   */
  @Override
  default @NotNull Iterator<E> iterator() {
    return Walkable.super.iterator();
  }

  /**
   * See {@link #iterator()} for details on the order.
   *
   * @return the walker
   */
  @Override
  @NotNull Walker<E> walker();

  /**
   * {@inheritDoc}
   *
   * @param action {@inheritDoc}
   */
  @Override
  default void forEach(Consumer<? super E> action) {
    Walkable.super.forEach(action);
  }

  /**
   * {@inheritDoc} // TODO Docs
   *
   * @return {@inheritDoc}
   */
  @Override
  default @NotNull Spliterator<E> spliterator() {
    return Walkable.super.spliterator();
  }


  /**
   * {@inheritDoc} // TODO Docs
   *
   * @return {@inheritDoc}
   */
  @Override
  default @NotNull Stream<E> stream() {
    return Walkable.super.stream();
  }

  /**
   * {@inheritDoc} // TODO Docs
   *
   * @return {@inheritDoc}
   */
  @Override
  default @NotNull Stream<E> parallelStream() {
    return Walkable.super.parallelStream();
  }

  /**
   * Compares the specified object with this for equality.
   * <p>
   * True if and only if
   * <ul>
   *   <li>{@code o} and this contain the same number of elements</li>
   *   <li>For each element in {@code o} there is a corresponding element in this <i>somewhere</i></li>
   *   <li>{@code o} is a {@code VBag} and NOT a {@code VSuccession} or {@code VPile}</li>
   * </ul>
   * Be wary that {@code (1, 1, 2)} is NOT equal to {@code (1, 2, 2)}.
   *
   * @param o object to test the equality of
   * @return true if the object is equal to this
   */
  @Override
  boolean equals(Object o);

  /**
   * Returns the hash code.
   * <p>
   * As always, if two objects are equal by {@code Objects.equals} then they have the same hash.
   *
   * @return the hash code
   */
  @Override
  int hashCode();
}
