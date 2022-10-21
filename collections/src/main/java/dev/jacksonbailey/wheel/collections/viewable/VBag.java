package dev.jacksonbailey.wheel.collections.viewable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Bags;
import dev.jacksonbailey.wheel.collections.Walkable;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VBag<E> extends Walkable<E> {

  int size();

  default boolean isEmpty() {
    return size() == 0;
  }

  default boolean contains(@Nullable Object o) {
    if (o == null) {
      return false;
    }
    return Bags.applyAcrossAll(this, false, true, e -> Objects.equals(e, o));
  }

  default boolean containsAll(@NotNull VBag<?> b) {
    return Bags.applyAcrossAll(requireNonNull(b), true, true, this::contains);
  }

  @NotNull VBag<E> shallowCopy();

}
