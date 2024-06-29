package dev.jacksonbailey.wheel.terra;

import static org.mockito.ArgumentMatchers.any;

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


}
