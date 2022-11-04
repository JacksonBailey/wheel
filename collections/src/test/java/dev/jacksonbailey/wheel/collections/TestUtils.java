package dev.jacksonbailey.wheel.collections;

import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public final class TestUtils {

  private TestUtils() {
  }

  /**
   * This method overcomes the issue with the original {@link org.mockito.Mockito#spy(Object)} when
   * passing a lambda which fails with an error saying that the passed class is final.
   *
   * @author Originally <a href="https://stackoverflow.com/users/859314">Alex Objelean</a>'s
   * <a href="https://stackoverflow.com/a/54328868/1858327">code</a> licensed under <a
   * href="https://creativecommons.org/licenses/by-sa/4.0/">CC-BY-SA 4.0</a>
   */
  @SuppressWarnings("unchecked")
  public static <R, P extends R> P spyLambda(final Class<R> lambdaType, final P lambda) {
    return (P) mock(lambdaType, delegatesTo(lambda));
  }

  public static <E> Iterable<E> toIterable(Iterator<E> iterator) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false)
                        .toList();
  }

}
