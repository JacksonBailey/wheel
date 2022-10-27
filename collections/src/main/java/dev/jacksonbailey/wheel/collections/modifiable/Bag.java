package dev.jacksonbailey.wheel.collections.modifiable;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.collections.Bags;
import dev.jacksonbailey.wheel.collections.viewable.VBag;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * In general these methods return true if the bag is modified.
 *
 * @param <E> the type of elements in the bag
 */
public interface Bag<E> extends VBag<E> {

  /**
   * Adds the element {@code e} to somewhere in this. Until it is {@code remove}d then this bag
   * {@code contains} it.
   * <p>
   * If this bag cannot hold {@code e} for whatever reason (subclasses may have restrictions) then
   * {@code false} will be returned to indicate that the bag was not modified.
   *
   * @param e the element to add
   * @return true if this was modified
   */
  boolean add(@NotNull E e);

  /**
   * Adds the elements of the bag {@code b} to somewhere in this bag.
   * <p>
   * There are no exact semantics other than each element in {@code b} will be passed to
   * {@link #add(Object)} and if any of the calls return {@code true} then this will too.
   *
   * @param b the bag of elements to add
   * @return true if this was modified
   * @see #add(Object)
   */
  default boolean addAll(@NotNull VBag<? extends E> b) {
    return Bags.applyAcrossAll(requireNonNull(b), false, false, this::add);
  }

  // TODO Consider returning Bags and Optionals of the removed elements?

  /**
   * Removes one copy of the element {@code o} from somewhere in the bag if one is present.
   *
   * @param o object to remove a copy of
   * @return true if this was modified
   */
  @Contract("!null -> _; null -> false")
  boolean remove(@Nullable Object o);

  // TODO Add a removeAll that removes all from this? As opposed to one per thing?

  /**
   * Removes one copy of the element {@code o} for each element in the bag {@code b} according to
   * {@link #remove(Object)} for each. Where {@code remove} is overridden it uses the logic of
   * that instead.
   *
   * @param b bag containing the objects to remove
   * @return true if this bag was modified
   */
  default boolean removeAll(@NotNull VBag<?> b) {
    return Bags.applyAcrossAll(requireNonNull(b), false, false, this::remove);
  }

  /**
   * Removes each element in this that satisfies {@code filter}.
   *
   * @param filter to test each element with
   * @return true if this bag was modified
   */
  default boolean removeIf(@NotNull Predicate<? super E> filter) {
    requireNonNull(filter);
    var copy = shallowCopy();
    return Bags.applyAcrossAll(copy, false, false, e -> filter.test(e) && remove(e));
  }

  /**
   * Retains one copy of each element for each element in {@code b}. Removes everything else.
   *
   * @param b bag containing the objects to retain
   * @return true if this bag was modified
   */
  default boolean retainAll(@NotNull VBag<?> b) {
    requireNonNull(b);
    var copy = shallowCopy();
    return Bags.applyAcrossAll(copy, false, false,
        e -> removeIf(Predicate.not(b::contains)));
  }

  /**
   * Removes all elements from this.
   *
   * @return true if this bag was modified
   */
  default boolean clear() {
    return Bags.applyAcrossAll(this, false, false, this::remove);
  }

  // TODO See where inheritdoc can be used

  @Override
  @Contract("-> new")
  @NotNull
  Bag<E> shallowCopy();

}
