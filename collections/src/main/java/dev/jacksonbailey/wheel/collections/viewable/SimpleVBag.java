package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.DelegatingWalker;
import dev.jacksonbailey.wheel.collections.Walker;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public class SimpleVBag<E> extends AbstractVBag<E> {

  private final Collection<E> collection;

  public SimpleVBag(Collection<E> collection) {
    this.collection = Collections.unmodifiableCollection(collection);
  }

  @Override
  public int size() {
    return collection.size();
  }

  @Override
  public @NotNull VBag<E> shallowCopy() {
    return new SimpleVBag<>(collection);
  }

  @Override
  public @NotNull Walker<E> walker() {
    return new DelegatingWalker<>(collection.iterator());
  }
}
