package dev.jacksonbailey.wheel.vexillum.db;

public class DbConfig {
  // TODO Where to put this and can I get the $buildDir in unit tests?
  public static final String dbFileLocation = "build/generated/db/vexillum.sqlite";
  public static final String jdbcUrl = "jdbc:sqlite:" + dbFileLocation;
}
