package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface ViewableSuccession<E> extends ViewableBag<E> {

  // TODO Rename to get()?
  default @NotNull Optional<E> element() {
    return getFirst();
  }

  @NotNull Optional<E> getFirst();

  @Override
  @NotNull ViewableSuccession<E> shallowCopy();

  // Walks head to tail
  @Override
  @NotNull Walker<E> walker();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }

}
