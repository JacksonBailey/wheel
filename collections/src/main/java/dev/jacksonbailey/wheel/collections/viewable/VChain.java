package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.modifiable.Chain;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public sealed interface VChain<E> extends VSuccession<E>, VPile<E> permits AbstractVChain, Chain {

  @Override
  @NotNull VChain<E> shallowCopy();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }
}
