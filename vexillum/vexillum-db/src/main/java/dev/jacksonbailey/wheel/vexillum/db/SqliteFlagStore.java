package dev.jacksonbailey.wheel.vexillum.db;

import dev.jacksonbailey.vexillum.db.jooq.tables.Flag;
import dev.jacksonbailey.wheel.vexillum.FlagStore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;

public class SqliteFlagStore implements FlagStore {

  private final Supplier<Connection> connectionSupplier;

  public SqliteFlagStore(String connectionString) {
    connectionSupplier = () -> {
      try {
        return DriverManager.getConnection(connectionString);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static SqliteFlagStore inMemory() {
    return new SqliteFlagStore("jdbc:sqlite::memory:");
  }

  @Override
  public Optional<Boolean> retrieveFlag(String flagName) {
    return Optional.empty();
  }

  private void ignoreThisMethod() {
    // This is just for testing that the Jooq stuff is on the classpath
    var flag = Flag.FLAG;
  }
}
