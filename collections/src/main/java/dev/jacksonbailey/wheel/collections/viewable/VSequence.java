package dev.jacksonbailey.wheel.collections.viewable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VSequence<E> extends VChain<E> {

  @Override
  @NotNull VSequence<E> shallowCopy();

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
