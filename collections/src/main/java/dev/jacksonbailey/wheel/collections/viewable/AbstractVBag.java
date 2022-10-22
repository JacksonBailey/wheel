package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;
import java.util.Objects;

// TODO Consider making a non-sealed interface instead of an abstract non-sealed class

/**
 * Abstract bag.
 * <p>
 * Due to the way sealed classes work this is also the parent class of all {@code VBag}s.
 *
 * @param <E> the type of elements in the bag
 */
public abstract non-sealed class AbstractVBag<E> implements VBag<E> {

  /**
   * {@inheritDoc}
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (o instanceof VBag<?> bag) {

      if (bag instanceof VSuccession<?> || bag instanceof VPile<?>) {
        return false;
      }

      return Bags.containsExactlyAll(this, bag);
    }

    return false;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Bags.hashingInOrder(walker());
  }

}
