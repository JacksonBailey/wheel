package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.Walker;
import dev.jacksonbailey.wheel.collections.viewable.VSuccession;
import java.util.Iterator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * First in, first out. Adds at tail/last and removes from head/first.
 */
public sealed interface Succession<E> extends VSuccession<E>, Bag<E>
    permits AbstractSuccession, Chain {

  // Adds at tail
  @Override
  default boolean add(@NotNull E e) {
    return addLast(e);
  }

  boolean addLast(@NotNull E e);

  // Removes from head
  @Override
  default boolean remove(@Nullable Object o) {
    return removeFirstOccurrence(o);
  }

  @NotNull Optional<E> removeFirst();

  boolean removeFirstOccurrence(@Nullable Object o);

  @Override
  @NotNull Succession<E> shallowCopy();

  // Walks head to tail
  @Override
  @NotNull Walker<E> walker();

  // Iterates head to tail
  @Override
  @NotNull Iterator<E> iterator();
}
