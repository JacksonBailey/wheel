package dev.jacksonbailey.wheel.collections.modifiable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.DelegatingWalker;
import dev.jacksonbailey.wheel.collections.Walker;
import java.util.ArrayList;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Reference implementation of bag.
 */
public class SimpleBag<E> extends AbstractBag<E> {

  @NotNull
  private final ArrayList<E> list;

  public SimpleBag() {
    this.list = new ArrayList<>();
  }

  public SimpleBag(@NotNull Iterable<E> iterable) {
    this();
    iterable.forEach(list::add);
    Collections.shuffle(list);
  }

  @Override
  @NotNull
  public Walker<E> walker() {
    return new DelegatingWalker<>(list.iterator());
  }

  @Override
  public boolean add(@NotNull E e) {
    var result = list.add(requireNonNull(e));
    Collections.shuffle(list);
    return result;
  }

  @Override
  public boolean remove(@Nullable Object o) {
    var result = list.remove(o);
    Collections.shuffle(list);
    return result;
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  @NotNull
  public Bag<E> shallowCopy() {
    return new SimpleBag<>(list);
  }
}
