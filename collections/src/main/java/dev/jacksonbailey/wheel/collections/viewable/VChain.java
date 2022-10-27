package dev.jacksonbailey.wheel.collections.viewable;

import java.util.Iterator;
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
  @Contract("-> new")
  @NotNull
  VChain<E> shallowCopy();

  @Override
  @NotNull
  default Iterator<E> iterator() {
    return VSuccession.super.iterator();
  }
  // TODO Double check this gets Succession iterator and walker

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
