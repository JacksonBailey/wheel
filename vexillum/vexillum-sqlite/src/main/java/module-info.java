module dev.jacksonbailey.wheel.vexillum.sqlite {
  requires static java.compiler;
  requires static org.jetbrains.annotations;
  requires dev.jacksonbailey.wheel.vexillum.core;
  requires org.flywaydb.core;
  requires org.jooq;
  requires org.slf4j;
  opens db.migration; // Allows migrations to be accessed on class path at runtime, TODO check this?
  provides dev.jacksonbailey.wheel.vexillum.FlagStoreFactory
      with dev.jacksonbailey.wheel.vexillum.sqlite.SqliteFlagStoreFactory;
}
