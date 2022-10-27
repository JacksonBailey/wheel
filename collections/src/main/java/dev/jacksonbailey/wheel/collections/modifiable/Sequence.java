package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.VSequence;
import org.jetbrains.annotations.NotNull;

public interface Sequence<E> extends VSequence<E>, Chain<E> {

  @Override
  @NotNull
  Sequence<E> shallowCopy();

  // TODO Make sure all methods that can be marked default due to a super actually are

}
