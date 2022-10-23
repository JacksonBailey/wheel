package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;

public abstract class AbstractVPile<E> extends AbstractVBag<E> implements VPileLeaf<E> {

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

    if (o instanceof VPile<?> pile && !(pile instanceof VChain<?>)) {
      return Bags.containsInOrder(descendingWalker(), pile.descendingWalker());
    }

    return false;
  }

  // TODO Does this cause collisions on Successions with the same elements and do we care?
  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Bags.hashingInOrder(descendingWalker());
  }

}
