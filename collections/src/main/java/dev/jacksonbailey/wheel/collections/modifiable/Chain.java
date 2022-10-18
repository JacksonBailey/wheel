package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.ViewableChain;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public interface Chain<E> extends ViewableChain<E>, Succession<E>, Pile<E> {

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addLast(e);
  }

  boolean addFirst(E e);

  // Removes from head
  @Override
  boolean remove(Object o);

  @Override
  @NotNull Chain<E> shallowCopy();

  // Iterates head to tail
  @Override
  default @NotNull Iterator<E> iterator() {
    return walker();
  }

}
