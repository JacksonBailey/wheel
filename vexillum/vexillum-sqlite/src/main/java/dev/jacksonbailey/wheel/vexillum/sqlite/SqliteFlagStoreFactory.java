package dev.jacksonbailey.wheel.vexillum.sqlite;

import dev.jacksonbailey.wheel.vexillum.FlagStore;
import dev.jacksonbailey.wheel.vexillum.FlagStoreFactory;

public class SqliteFlagStoreFactory implements FlagStoreFactory {

  @Override
  public FlagStore create(String connectionString) {
    return new SqliteFlagStore(connectionString);
  }
}
