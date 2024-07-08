package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a channel and how it should be configured.
 */
public interface ChannelDefinition {

  String name();

  ChannelType type();

  Integer position();

  List<Overwrite> permissionOverwrites();

  @NotNull
  static ChannelDefinition from(@NotNull Channel channel) {
    return switch (channel) {
      case TextChannel textChannel -> TextChannelDefinition.from(textChannel);
      case VoiceChannel voiceChannel -> VoiceChannelDefinition.from(voiceChannel);
      case Category category -> CategoryDefinition.from(category);
      case NewsChannel newsChannel -> AnnouncementChannelDefinition.from(newsChannel);
      case StageChannel stageChannel -> StageChannelDefinition.from(stageChannel);
      case ForumChannel forumChannel -> ForumChannelDefinition.from(forumChannel);
      case MediaChannel mediaChannel -> MediaChannelDefinition.from(mediaChannel);
      default -> throw new IllegalArgumentException(
          "Unexpected type: " + channel.getClass() + ", " + channel
      );
    };
  }

}
