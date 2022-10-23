package dev.jacksonbailey.wheel.collections.viewable;

/**
 * Bags make heavy use of {@code sealed interface}s. To implement one directly without using the
 * {@code abstract class} provided use these "leaf" interfaces which are {@code non-sealed}.
 *
 * @param <E> the type of elements in the bag
 */
public non-sealed interface VChainLeaf<E> extends VChain<E> {

}
