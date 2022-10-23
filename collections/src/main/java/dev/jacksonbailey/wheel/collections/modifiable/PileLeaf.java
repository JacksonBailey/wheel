package dev.jacksonbailey.wheel.collections.modifiable;

/**
 * Bags make heavy use of {@code sealed interface}s. To implement one directly without using the
 * {@code abstract class} provided use these "leaf" interfaces which are {@code non-sealed}.
 *
 * @param <E> the type of elements in the bag
 */
public non-sealed interface PileLeaf<E> extends Pile<E> {

}
