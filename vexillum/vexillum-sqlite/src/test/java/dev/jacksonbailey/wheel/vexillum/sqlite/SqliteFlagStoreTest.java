package dev.jacksonbailey.wheel.vexillum.sqlite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SqliteFlagStoreTest {

  private SqliteFlagStore flagStore;

  @BeforeEach
  void beforeEach(@TempDir Path tempDir) {
    var jdbcConnectionString = "jdbc:sqlite:" + tempDir.resolve("test.db");
    flagStore = new SqliteFlagStore(jdbcConnectionString);
  }

  @Test
  void testUpsertWhenEmpty() {
    var actual = flagStore.upsertFlag("WOW", true);
    assertEquals(Optional.empty(), actual);
  }

  @Test
  void testUpsertWithExistingValue() {
    flagStore.upsertFlag("WOW", false);
    var actual = flagStore.upsertFlag("WOW", true);
    assertEquals(Optional.of(false), actual);
  }

}
