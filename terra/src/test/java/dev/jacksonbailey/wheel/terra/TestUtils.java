package dev.jacksonbailey.wheel.terra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public final class TestUtils {

  private TestUtils() {
    throw new AssertionError();
  }

  public static <T> Consumer<T> anyConsumerOf(T t) {
    @SuppressWarnings("unchecked")
    Consumer<T> toReturn = any(Consumer.class);
    return toReturn;
  }

  public static <T> Consumer<T> nullableConsumerOf(T t) {
    @SuppressWarnings("unchecked")
    Consumer<T> toReturn = nullable(Consumer.class);
    return toReturn;
  }

  public static <T> Future<T> anyFutureOf(T t) {
    @SuppressWarnings("unchecked")
    Future<T> toReturn = any(Future.class);
    return toReturn;
  }

}
