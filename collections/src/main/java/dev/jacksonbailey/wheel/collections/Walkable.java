package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;

public interface Walkable<E> extends Iterable<E> {
  // TODO Consider extending to one that forces remove to be present for easy clear

  Walker<E> walker();

  // TODO Iterator or Walker here?
  default Iterator<E> iterator() {
    return walker();
  }
}
