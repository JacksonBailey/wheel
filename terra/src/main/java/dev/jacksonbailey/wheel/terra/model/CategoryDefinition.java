package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import lombok.Builder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.Category;

@Builder(toBuilder = true)
public record CategoryDefinition(
    String name,
    ChannelType type,
    Integer position,
    List<Overwrite> permissionOverwrites
) implements ChannelDefinition {

  public CategoryDefinition {
    if (!ChannelType.CATEGORY.equals(type)) {
      throw new IllegalArgumentException("Expected CATEGORY, got " + type.name());
    }
  }

  public static CategoryDefinition from(Category category) {
    return CategoryDefinition
        .builder()
        .name(category.getName())
        .type(category.getType())
        .position(category.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .build();
  }

}
