package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import lombok.Builder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.attribute.IPostContainer.SortOrder;
import net.dv8tion.jda.api.entities.channel.concrete.MediaChannel;

// TODO Verify this really does not have nsfw field
@Builder(toBuilder = true)
public record MediaChannelDefinition(
    String name,
    ChannelType type,
    String topic,
    Integer rateLimitPerUser,
    Integer position,
    List<Overwrite> permissionOverwrites,
    String parent,
    Integer defaultAutoArchiveDuration,
    DefaultReaction defaultReactionEmoji,
    List<Object> availableTags, // TODO Tag type
    SortOrder defaultSortOrder,
    Integer defaultThreadRateLimitPerUser
) implements ChannelDefinition {

  public MediaChannelDefinition {
    if (!ChannelType.MEDIA.equals(type)) {
      throw new IllegalArgumentException("Expected MEDIA, got " + type.name());
    }
  }

  public static MediaChannelDefinition from(MediaChannel mediaChannel) {
    return MediaChannelDefinition
        .builder()
        .name(mediaChannel.getName())
        .type(mediaChannel.getType())
        .topic(mediaChannel.getTopic())
        .rateLimitPerUser(null) // TODO Where is this?
        .position(mediaChannel.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .defaultReactionEmoji(null) // TODO Too lazy right now
        .availableTags(null) // TODO Too lazy right now
        .defaultSortOrder(mediaChannel.getDefaultSortOrder())
        .defaultThreadRateLimitPerUser(mediaChannel.getDefaultThreadSlowmode()) // TODO Right field?
        .build();
  }

}
