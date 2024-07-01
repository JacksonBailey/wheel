package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@Builder(toBuilder = true)
public record TextChannelDefinition(
    String name,
    ChannelType type,
    String topic,
    Integer rateLimitPerUser,
    Integer position,
    List<Overwrite> permissionOverwrites,
    String parent,
    Boolean nsfw,
    Integer defaultAutoArchiveDuration,
    Integer defaultThreadRateLimitPerUser
) implements ChannelDefinition {

  public TextChannelDefinition {
    if (!ChannelType.TEXT.equals(type)) {
      throw new IllegalArgumentException("Expected TEXT, got " + type.name());
    }
  }

  public static TextChannelDefinition from(TextChannel textChannel) {
    return TextChannelDefinition
        .builder()
        .name(textChannel.getName())
        .type(textChannel.getType())
        .topic(textChannel.getTopic())
        .rateLimitPerUser(null) // TODO Where is this?
        .position(textChannel.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .parent(
            Optional.ofNullable(textChannel.getParentCategory())
                    .map(Channel::getName)
                    .orElse(null)
        )
        .nsfw(textChannel.isNSFW())
        .defaultAutoArchiveDuration(null) // TODO Where?
        .defaultThreadRateLimitPerUser(textChannel.getDefaultThreadSlowmode())
        .build();
  }

}
