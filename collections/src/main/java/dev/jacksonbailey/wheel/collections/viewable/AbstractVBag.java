package dev.jacksonbailey.wheel.collections.viewable;

import java.util.ArrayList;

public abstract non-sealed class AbstractVBag<E> implements VBag<E> {

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof VBag<?> b) {
      if (b instanceof VSuccession<?> || b instanceof VPile<?>) {
        return false;
      }
      if (size() != b.size()) {
        return false;
      }
      var thisList = new ArrayList<E>();
      shallowCopy().forEach(thisList::add);
      var thatList = new ArrayList<>();
      b.forEach(thatList::add);
      for (E e : thisList) {
        if (!thatList.remove(e)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

}
