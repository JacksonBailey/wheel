package dev.jacksonbailey.wheel.collections.modifiable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Walkable;
import dev.jacksonbailey.wheel.collections.Walker;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 * In general these methods return true if the bag is modified.
 */
public interface Bag<E> extends Walkable<E> {

  boolean add(E e);
  default boolean addAll(Bag<? extends E> b) {
    return applyToAll(this::add, b);
  }

  boolean remove(Object o);
  default boolean removeAll(Bag<?> b) {
    return applyToAll(this::remove, b);
  }

  // TODO Is there a way to do this without optional operation?
  // TODO Should modifying a Bag while walking be undefined like JCL?
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

  default boolean retainAll(Bag<?> b) {
    return applyToAll(e -> removeIf(Predicate.not(b::contains)), b);
  }
  void clear();

  // Looking
  default boolean contains(Object o) {
    for (E e: this) {
      if (Objects.equals(o, e)) {
        return true;
      }
    }
    return false;
  }
  boolean containsAll(Bag<?> b);

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

  private static <B> boolean applyToAll(Predicate<? super B> p, Bag<B> b) {
    // rollingOr returns true if any p.test(e) is true
    var rollingOr = new AtomicBoolean(false);
    b.forEach(e -> rollingOr.compareAndSet(false, p.test(e)));
    return rollingOr.get();
  }

}
