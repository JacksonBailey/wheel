package dev.jacksonbailey.wheel.vexillum;

public interface FlagStoreFactory {
  FlagStore create(String connectionString);
}
