package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Walker<E> extends Iterator<E> {

  @NotNull
  default Optional<E> maybeNext() {
    if (hasNext()) {
      return Optional.of(next());
    }
    return Optional.empty();
  }

}
