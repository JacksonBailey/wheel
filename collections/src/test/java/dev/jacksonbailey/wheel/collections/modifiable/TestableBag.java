package dev.jacksonbailey.wheel.collections.modifiable;

import dev.jacksonbailey.wheel.collections.TestableWalker;
import dev.jacksonbailey.wheel.collections.Walker;
import java.util.ArrayList;
import java.util.List;

public class TestableBag<E> implements Bag<E> {

  private final List<E> list;

  public TestableBag() {
    this.list = new ArrayList<>();
  }

  public TestableBag(List<E> list) {
    this.list = new ArrayList<>(list);
  }

  @Override
  public boolean add(E e) {
    return list.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public Bag<E> shallowCopy() {
    return new TestableBag<>(new ArrayList<>(list));
  }

  @Override
  public Walker<E> walker() {
    return new TestableWalker<>(list.iterator());
  }
}
