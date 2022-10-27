package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.viewable.VBag;
import dev.jacksonbailey.wheel.collections.viewable.VChain;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Chain<E> extends VChain<E>, Succession<E>, Pile<E> {

  @Override
  default boolean add(@NotNull E e) {
    return Succession.super.add(e);
  }

  @Override
  default boolean addAll(@NotNull VBag<? extends E> b) {
    return Succession.super.addAll(b);
  }

  /**
   * Adds the element {@code e} to the head of this.
   *
   * @param e the element to add
   * @return true if this was modified
   */
  boolean addHead(@NotNull E e);

  @Override
  @Contract("!null -> _; null -> false")
  default boolean remove(@Nullable Object o) {
    return Succession.super.remove(o);
  }

  @Override
  default boolean removeAll(@NotNull VBag<?> b) {
    return Succession.super.removeAll(b);
  }

  @Override
  default boolean retainAll(@NotNull VBag<?> b) {
    return Succession.super.retainAll(b);
  }

  @Override
  @Contract("-> new")
  @NotNull
  Chain<E> shallowCopy();

}
