module dev.jacksonbailey.wheel.vexillum.impl {
  requires transitive dev.jacksonbailey.wheel.vexillum.core;
  requires org.flywaydb.core;
  exports dev.jacksonbailey.wheel.vexillum.impl;
  uses dev.jacksonbailey.wheel.vexillum.FlagStoreFactory;
}
