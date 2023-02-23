package dev.jacksonbailey.wheel.vexillum.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import dev.jacksonbailey.wheel.vexillum.impl.Sample;
import org.junit.jupiter.api.Test;

class SampleTest {

  @Test
  void shouldLoadSqliteFlagStore() {
    // Memory dbs do not work but we are only testing service loading
    var connectionString = "jdbc:sqlite::memory:";
    var sample = new Sample(connectionString);
    assertNotNull(sample.getFlagStore());
  }

}
