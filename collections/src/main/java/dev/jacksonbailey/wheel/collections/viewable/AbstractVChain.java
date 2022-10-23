package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;

public abstract class AbstractVChain<E> extends AbstractVSuccession<E>
    implements VChainLeaf<E> {

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

    if (o instanceof VChain<?> chain) { // TODO And NOT some other type of bag
      return Bags.containsInOrder(walker(), chain.walker());
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
