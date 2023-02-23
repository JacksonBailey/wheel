package dev.jacksonbailey.wheel.vexillum.impl;

import dev.jacksonbailey.wheel.vexillum.FlagStore;
import dev.jacksonbailey.wheel.vexillum.FlagStoreFactory;
import java.util.ServiceLoader;

public class Sample {

  private final FlagStore flagStore;

  public Sample(String connectionString) {
    var flagStoreFactory = ServiceLoader.load(FlagStoreFactory.class)
                                        .findFirst()
                                        .orElseThrow();
    flagStore = flagStoreFactory.create(connectionString);
  }

  public FlagStore getFlagStore() {
    return flagStore;
  }

  // This is here to check that we can load the service at runtime, we can remove it later if needed
  public static void main(String[] args) {
    var sample = new Sample("jdbc:sqlite::memory:");
    System.out.println(sample.getFlagStore().getClass());
  }
}
