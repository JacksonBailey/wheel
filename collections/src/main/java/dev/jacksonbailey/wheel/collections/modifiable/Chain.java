package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VChain;
import java.util.Iterator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface Chain<E> extends VChain<E>, Succession<E>, Pile<E> permits ChainLeaf {

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addTail(e);
  }

  boolean addHead(@NotNull E e);

  // Removes from head
  @Override
  @Contract("!null -> _; null -> false")
  boolean remove(@Nullable Object o);

  @Override
  @Contract("-> new")
  @NotNull Chain<E> shallowCopy();

  // Walks head to tail
  @Override
  @NotNull Walker<E> walker();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }

}
