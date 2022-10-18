package dev.jacksonbailey.wheel.collections.viewable;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public interface ViewableChain<E> extends ViewableSuccession<E>, ViewablePile<E> {

  @Override
  @NotNull ViewableChain<E> shallowCopy();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }
}
