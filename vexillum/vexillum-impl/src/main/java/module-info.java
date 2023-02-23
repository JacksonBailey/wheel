module dev.jacksonbailey.wheel.vexillum.impl {
  requires dev.jacksonbailey.wheel.vexillum.core;
  requires dev.jacksonbailey.wheel.vexillum.sqlite;
  requires org.flywaydb.core;
  requires org.jooq;
  requires org.slf4j;
  exports dev.jacksonbailey.wheel.vexillum.impl;
  uses dev.jacksonbailey.wheel.vexillum.FlagStoreFactory;
}
