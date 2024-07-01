package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.attribute.IPostContainer.SortOrder;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel.Layout;

@Builder(toBuilder = true)
public record ForumChannelDefinition(
    String name,
    ChannelType type,
    String topic,
    Integer rateLimitPerUser,
    Integer position,
    List<Overwrite> permissionOverwrites,
    String parent,
    Boolean nsfw,
    Integer defaultAutoArchiveDuration,
    DefaultReaction defaultReactionEmoji,
    List<Object> availableTags, // TODO Tag type
    SortOrder defaultSortOrder,
    Layout defaultForumLayout,
    Integer defaultThreadRateLimitPerUser
) implements ChannelDefinition {

  public ForumChannelDefinition {
    if (!ChannelType.FORUM.equals(type)) {
      throw new IllegalArgumentException("Expected FORUM, got " + type.name());
    }
  }

  public static ForumChannelDefinition from(ForumChannel forumChannel) {
    return ForumChannelDefinition
        .builder()
        .name(forumChannel.getName())
        .type(forumChannel.getType())
        .topic(forumChannel.getTopic())
        .rateLimitPerUser(null) // TODO Where is this?
        .position(forumChannel.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .parent(
            Optional.ofNullable(forumChannel.getParentCategory())
                    .map(Channel::getName)
                    .orElse(null)
        )
        .nsfw(forumChannel.isNSFW())
        .defaultAutoArchiveDuration(null) // TODO Where is this?
        .defaultReactionEmoji(null) // TODO Too lazy right now
        .availableTags(null) // TODO Too lazy right now
        .defaultSortOrder(forumChannel.getDefaultSortOrder())
        .defaultForumLayout(forumChannel.getDefaultLayout())
        .defaultThreadRateLimitPerUser(forumChannel.getDefaultThreadSlowmode()) // TODO Right field?
        .build();
  }

}
