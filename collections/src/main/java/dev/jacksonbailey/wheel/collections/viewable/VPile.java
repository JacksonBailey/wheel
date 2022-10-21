package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.modifiable.Pile;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public sealed interface VPile<E> extends VBag<E> permits AbstractVPile, VChain, Pile {

  @NotNull Optional<E> getLast();

  @Override
  @NotNull VPile<E> shallowCopy();

  // Does not necessarily iterate in any order, *wink*
  @Override
  @NotNull Walker<E> walker();

  // Walks tail to head
  @NotNull Walker<E> descendingWalker();

  // Does not necessarily iterate in any order, *wink*
  @Override
  @NotNull Iterator<E> iterator();

  // Iterates tail to head
  default @NotNull Iterator<E> descendingIterator() {
    return walker();
  }

}
