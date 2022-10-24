package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * "Reference implementation" that wraps an iterator.
 */
public class DelegatingWalker<E> implements Walker<E> {

  @NotNull
  private final Iterator<E> iterator;

  public DelegatingWalker(@NotNull Iterator<E> iterator) {
    this.iterator = iterator;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  @NotNull
  public E next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    iterator.remove();
  }

  @Override
  public void forEachRemaining(@NotNull Consumer<? super E> action) {
    iterator.forEachRemaining(action);
  }

  @Override
  @NotNull
  public Optional<E> maybeNext() {
    return Walker.super.maybeNext();
  }
}
