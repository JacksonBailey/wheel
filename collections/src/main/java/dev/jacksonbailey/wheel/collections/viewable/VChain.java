package dev.jacksonbailey.wheel.collections.viewable;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public interface VChain<E> extends VSuccession<E>, VPile<E> {

  @Override
  @NotNull VChain<E> shallowCopy();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }
}
