package dev.jacksonbailey.wheel.collections.modifiable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Walkable;
import dev.jacksonbailey.wheel.collections.Walker;
import java.util.function.Predicate;

public interface Bag<E> extends Walkable<E> {

  boolean add(E e);
  boolean addAll(Bag<? extends E> c);

  boolean remove(Object o);
  boolean removeAll(Bag<?> c);

  // TODO Is there a way to do this without optional operation?
  // TODO Should modifying a Bag while walking be undefined?
  default boolean removeIf(Predicate<? super E> filter) throws UnsupportedOperationException {
    requireNonNull(filter);
    var modified = false;
    var walker = walker();
    while (walker.hasNext()) {
      if (filter.test(walker.next())) {
        walker.remove();
        modified = true;
      }
    }
    return modified;
  }

  boolean retainAll(Bag<?> c);
  void clear();

  // Looking
  boolean contains(Object o);
  boolean containsAll(Bag<?> c);

  // Sizing
  default boolean isEmpty() {
    return size() == 0;
  }
  int size();

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  Walker<E> walker();

}
