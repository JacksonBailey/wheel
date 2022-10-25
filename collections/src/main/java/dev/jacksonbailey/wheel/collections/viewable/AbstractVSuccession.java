package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;

public abstract class AbstractVSuccession<E> extends AbstractVBag<E> implements VSuccessionLeaf<E> {

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

  @Override
  public int hashCode() {
    return Bags.hashingInOrder(walker());
  }

}
