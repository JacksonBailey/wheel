package dev.jacksonbailey.wheel.terra.model;

/**
 * <a href="https://discord.com/developers/docs/reference#snowflakes">Discord Snowflake docs</a>.
 */
// JDA does not provide anything to merely store the Snowflake ID.
public record Snowflake(long bits) {

  // TODO Consider implementing a way to get the timestamp?

  public static Snowflake of(String s) {
    return new Snowflake(Long.parseUnsignedLong(s));
  }

}
