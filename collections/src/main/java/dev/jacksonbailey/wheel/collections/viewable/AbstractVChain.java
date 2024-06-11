package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;

public abstract class AbstractVChain<E> extends AbstractVSuccession<E>
    implements VChain<E> {

  public AbstractVChain() {}

  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }

    if (o instanceof VChain<?> chain && !(chain instanceof VSequence<?>)) {
      return Bags.containsInOrder(walker(), chain.walker());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Bags.productHashingInOrder(walker());
  }

}
