package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;
import java.util.Objects;

public abstract non-sealed class AbstractVSuccession<E> extends AbstractVBag<E>
    implements VSuccession<E> {

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

    if (o instanceof VSuccession<?> succession && !(succession instanceof VChain<?>)) {
      return Bags.containsInOrder(walker(), succession.walker());
    }

    return false;
  }

  /**
   * {@inheritDoc}
   *
   * @implNote This currently has the same implementation as {@link AbstractVBag#hashCode()}.
   *
   * @return {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Bags.hashingInOrder(walker());
  }

}
