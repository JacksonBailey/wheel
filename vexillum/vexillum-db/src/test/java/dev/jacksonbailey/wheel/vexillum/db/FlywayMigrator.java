package dev.jacksonbailey.wheel.vexillum.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayMigrator implements BeforeEachCallback {

  private static final Logger log = LoggerFactory.getLogger(FlywayMigrator.class);

  @Override
  public void beforeEach(ExtensionContext context) {
    touchDatabase();
    remakeDatabase();
  }

  private void touchDatabase() {
    try {
      var dbDirectory = Path.of("build", "generated", "db");
      Files.createDirectories(dbDirectory);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try (var connection = DriverManager.getConnection(DbConfig.jdbcUrl)) {
      log.trace("Successfully connected (and possibly created) database file, {}", connection);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void remakeDatabase() {
    var flyway = Flyway.configure()
                       .dataSource(DbConfig.jdbcUrl, null, null)
                       .cleanDisabled(false)
                       .load();
    flyway.clean();
    flyway.migrate();
  }
}
