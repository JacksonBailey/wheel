package dev.jacksonbailey.wheel.collections.viewable;

import java.util.ArrayList;

// TODO Consider making a non-sealed interface instead of an abstract non-sealed class

/**
 * Abstract bag.
 * <p>
 * Due to the way sealed classes work this is also the parent class of all {@code VBag}s.
 *
 * @param <E> the type of elements in the bag
 */
public abstract non-sealed class AbstractVBag<E> implements VBag<E> {

  // TODO As far as I can tell there's no reason why this equals impl can't be on VBag? What was I thinking?

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
