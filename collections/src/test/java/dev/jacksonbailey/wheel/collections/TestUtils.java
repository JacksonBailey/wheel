package dev.jacksonbailey.wheel.collections;

import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;

public final class TestUtils {

  private TestUtils() {}

  // Author: Alex Objelean https://stackoverflow.com/users/859314
  // Source: https://stackoverflow.com/a/54328868/1858327
  // License: https://creativecommons.org/licenses/by-sa/4.0/
  /**
   * This method overcomes the issue with the original {@link org.mockito.Mockito#spy(Object)} when
   * passing a lambda which fails with an error saying that the passed class is final.
   */
  @SuppressWarnings("unchecked")
  public static <R, P extends R> P spyLambda(final Class<R> lambdaType, final P lambda) {
    return (P) mock(lambdaType, delegatesTo(lambda));
  }

}
