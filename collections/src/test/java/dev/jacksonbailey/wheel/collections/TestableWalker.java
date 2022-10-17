package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import java.util.function.Consumer;

public class TestableWalker<E> implements Walker<E> {

  private final Iterator<E> delegate;

  public TestableWalker(Iterator<E> delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean hasNext() {
    return delegate.hasNext();
  }

  @Override
  public E next() {
    return delegate.next();
  }

  @Override
  public void remove() {
    delegate.remove();
  }

  @Override
  public void forEachRemaining(Consumer<? super E> action) {
    delegate.forEachRemaining(action);
  }
}
