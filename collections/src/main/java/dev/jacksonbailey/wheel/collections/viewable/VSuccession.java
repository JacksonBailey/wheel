package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Succession;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public sealed interface VSuccession<E> extends VBag<E>
    permits AbstractVSuccession, VChain, Succession {

  // TODO Rename to get()?
  default @NotNull Optional<E> element() {
    return getFirst();
  }

  @NotNull Optional<E> getFirst();

  @Override
  @NotNull VSuccession<E> shallowCopy();

  // Walks head to tail
  @Override
  @NotNull Walker<E> walker();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }

}
