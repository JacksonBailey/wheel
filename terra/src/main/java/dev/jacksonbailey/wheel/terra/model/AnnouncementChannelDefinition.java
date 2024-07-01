package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;

/**
 * These used to be called News Channels in the Discord API and still are in JDA.
 */
@Builder(toBuilder = true)
public record AnnouncementChannelDefinition(
    String name,
    ChannelType type,
    String topic,
    Integer position,
    List<Overwrite> permissionOverwrites,
    String parent,
    Boolean nsfw,
    Integer defaultAutoArchiveDuration,
    Integer defaultThreadRateLimitPerUser
) implements ChannelDefinition {

  public AnnouncementChannelDefinition {
    if (!ChannelType.NEWS.equals(type)) {
      throw new IllegalArgumentException("Expected NEWS (Announcement), got " + type.name());
    }
  }

  public static AnnouncementChannelDefinition from(NewsChannel newsChannel) {
    return AnnouncementChannelDefinition
        .builder()
        .name(newsChannel.getName())
        .type(newsChannel.getType())
        .topic(newsChannel.getTopic())
        .position(newsChannel.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .parent(
            Optional.ofNullable(newsChannel.getParentCategory())
                    .map(Channel::getName)
                    .orElse(null)
        )
        .nsfw(newsChannel.isNSFW())
        .defaultAutoArchiveDuration(null) // TODO Where is this?
        .defaultThreadRateLimitPerUser(newsChannel.getDefaultThreadSlowmode()) // TODO Right field?
        .build();
  }

}
