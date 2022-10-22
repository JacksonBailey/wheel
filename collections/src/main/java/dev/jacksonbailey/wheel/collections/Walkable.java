package dev.jacksonbailey.wheel.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;

public interface Walkable<E> extends Iterable<E> {

  @NotNull Walker<E> walker();

  // TODO Iterator or Walker here?
  default @NotNull Iterator<E> iterator() {
    return walker();
  }

  @Override
  default @NotNull Spliterator<E> spliterator() {
    return Iterable.super.spliterator();
  }

  default @NotNull Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  default @NotNull Stream<E> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
  }
}
