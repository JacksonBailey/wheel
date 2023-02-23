module dev.jacksonbailey.wheel.vexillum.impl.test {
  requires transitive dev.jacksonbailey.wheel.vexillum.impl;
  requires org.junit.jupiter.api;
  opens dev.jacksonbailey.wheel.vexillum.impl.test to org.junit.platform.commons;
}
