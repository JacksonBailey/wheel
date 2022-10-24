package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;

public interface Walkable<E> extends Iterable<E> {

  // TODO Specify that Walkable and Walker make shallow copies?

  @NotNull
  Walker<E> walker();

  // TODO Iterator or Walker here? If you do Walker then no need for walker() methods!
  @NotNull
  default Iterator<E> iterator() {
    return walker();
  }

  @Override
  @NotNull
  default Spliterator<E> spliterator() {
    return Iterable.super.spliterator();
  }

  @NotNull
  default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  @NotNull
  default Stream<E> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
  }
}
