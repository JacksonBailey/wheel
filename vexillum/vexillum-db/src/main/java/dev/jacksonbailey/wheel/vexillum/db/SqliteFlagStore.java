package dev.jacksonbailey.wheel.vexillum.db;

import static dev.jacksonbailey.vexillum.db.jooq.Tables.FLAG;

import dev.jacksonbailey.vexillum.db.jooq.tables.records.FlagRecord;
import dev.jacksonbailey.wheel.vexillum.FlagStore;
import java.util.Optional;
import java.util.function.Supplier;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.Range;
import org.jooq.CloseableDSLContext;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqliteFlagStore implements FlagStore {

  private static final Logger log = LoggerFactory.getLogger(SqliteFlagStore.class);

  private final Supplier<CloseableDSLContext> createSupplier;

  public SqliteFlagStore(String connectionString) {
    createSupplier = () -> DSL.using(connectionString);
    log.debug("Creating a new flag store with connection string '{}'", connectionString);
    Flyway.configure()
          .dataSource(connectionString, null, null)
          .loggers("slf4j")
          .load()
          .migrate();
  }

  @Override
  public Optional<Boolean> retrieveFlag(String flagName) {
    return Optional.empty();
  }

  public Optional<Boolean> upsertFlag(String flagName, boolean state) {
    Optional<FlagRecord> prev;
    try (var create = createSupplier.get()) {
      prev = create.selectFrom(FLAG)
                   .where(FLAG.NAME.eq(flagName))
                   .fetchOptional();
      create.insertInto(FLAG, FLAG.NAME, FLAG.STATE)
            .values(flagName, boolToInt(state))
            .onConflict(FLAG.NAME)
            .doUpdate()
            .set(FLAG.STATE, boolToInt(state))
            .execute();
    }
    return prev.map(FlagRecord::getState)
               .map(SqliteFlagStore::intToBool);
  }

  private static int boolToInt(boolean b) {
    log.trace("Converting {}", b);
    if (b) {
      return 1;
    }
    return 0;
  }

  private static boolean intToBool(@Range(from = 0, to = 1) int i) {
    log.trace("Converting {}", i);
    return i != 0;
  }
}
