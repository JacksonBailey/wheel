package dev.jacksonbailey.wheel.terra.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record DefaultReaction(Snowflake emojiId, String emojiName) {

  public DefaultReaction {
    if ((emojiId == null) == (emojiName == null)) {
      throw new IllegalArgumentException("Exactly one of the fields must be null");
    }
  }

  public DefaultReaction(Snowflake emojiId) {
    this(emojiId, null);
  }

  public DefaultReaction(String emojiName) {
    this(null, emojiName);
  }
}
