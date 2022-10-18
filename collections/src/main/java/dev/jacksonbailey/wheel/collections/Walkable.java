package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public interface Walkable<E> extends Iterable<E> {

  @NotNull Walker<E> walker();

  // TODO Iterator or Walker here?
  default @NotNull Iterator<E> iterator() {
    return walker();
  }
}
