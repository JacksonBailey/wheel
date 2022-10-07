package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import java.util.Optional;

public interface Walker<E> extends Iterator<E> {

  default Optional<E> maybeNext() {
    if (hasNext()) {
      return Optional.of(next());
    }
    return Optional.empty();
  }

}
