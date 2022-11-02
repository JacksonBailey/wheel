package dev.jacksonbailey.wheel.collections.viewable;

import dev.jacksonbailey.wheel.collections.DelegatingWalker;
import dev.jacksonbailey.wheel.collections.Walker;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public class SimpleVBag<E> extends AbstractVBag<E> {

  private final Collection<E> collection;

  public SimpleVBag(@NotNull Collection<E> collection) {
    /*
     * Collection.contains(null) is allowed to throw NPE if the collection cannot contain nulls so
     * this code catches and ignores that exception then assumes the collection did its due
     * diligence in making sure there are no nulls already in the collection.
     */
    boolean containsNull = false;
    try {
      containsNull = collection.contains(null);
    } catch (NullPointerException ignored) {
    }
    // Then if a null *was* in the collection this throws an exception because bags do not allow it
    if (containsNull) {
      throw new NullPointerException("No nulls allowed");
    }
    this.collection = Collections.unmodifiableCollection(collection);
  }

  @Override
  public int size() {
    return collection.size();
  }

  /*
   * TODO I am annoyed that the IntelliJ NotNull annotation is both a method and type annotation.
   * I am considering replacing it with something else, maybe checker framework? Maybe nothing?
   * When generating methods it puts it after public but before I manually moved them all. I don't
   * want to again.
   */

  @Override
  public @NotNull VBag<E> shallowCopy() {
    return new SimpleVBag<>(collection);
  }

  @Override
  public @NotNull Walker<E> walker() {
    return new DelegatingWalker<>(collection.iterator());
  }
}
