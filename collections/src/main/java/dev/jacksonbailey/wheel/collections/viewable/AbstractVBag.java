package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Bags;

public abstract class AbstractVBag<E> implements VBag<E> {

  public AbstractVBag() {}

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

  @Override
  public int hashCode() {
    return Bags.sumHashing(this);
  }

}
