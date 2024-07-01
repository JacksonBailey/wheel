package dev.jacksonbailey.wheel.terra.service;

import dev.jacksonbailey.wheel.terra.model.AnnouncementChannelDefinition;
import dev.jacksonbailey.wheel.terra.model.CategoryDefinition;
import dev.jacksonbailey.wheel.terra.model.ChannelDefinition;
import dev.jacksonbailey.wheel.terra.model.ForumChannelDefinition;
import dev.jacksonbailey.wheel.terra.model.MediaChannelDefinition;
import dev.jacksonbailey.wheel.terra.model.StageChannelDefinition;
import dev.jacksonbailey.wheel.terra.model.TextChannelDefinition;
import dev.jacksonbailey.wheel.terra.model.VoiceChannelDefinition;
import java.util.ArrayList;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Gets channel definitions from existing channels.
 */
@Service
public class GetChannelsCommand extends AbstractCommand {

  private static final Logger log = LoggerFactory.getLogger(GetChannelsCommand.class);

  public static String COMMAND_NAME = "get-channels";

  public GetChannelsCommand(JDA jda) {
    super(jda);
  }

  @Override
  public CommandData getCommandData() {
    return Commands.slash(COMMAND_NAME, "Get channel definitions")
                   .setDefaultPermissions(
                       DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL)
                   )
                   .setGuildOnly(true);
  }

  @Override
  public MessageCreateData doCommand(@NotNull SlashCommandInteractionEvent event) {
    var sourceGuild = event.getGuild();
    assert sourceGuild != null; // TODO Improve this??

    // TODO At some point this should be in a utility class.
    var channels = sourceGuild.getChannels();
    var channelDefinitions = new ArrayList<ChannelDefinition>();
    for (var channel : channels) {
      var channelType = channel.getType();
      switch (channelType) {
        case TEXT -> {
          if (channel instanceof TextChannel textChannel) {
            channelDefinitions.add(TextChannelDefinition.from(textChannel));
          } else {
            throw new IllegalStateException();
          }
        }
        case PRIVATE -> {
          log.warn("Got a channel of type PRIVATE which should not be in guilds: {}", channel);
        }
        case VOICE -> {
          if (channel instanceof VoiceChannel voiceChannel) {
            channelDefinitions.add(VoiceChannelDefinition.from(voiceChannel));
          } else {
            throw new IllegalStateException();
          }
        }
        case GROUP -> {
          log.warn("Got a channel of type GROUP which should not exist: {}", channel);
        }
        case CATEGORY -> {
          if (channel instanceof Category category) {
            channelDefinitions.add(CategoryDefinition.from(category));
          } else {
            throw new IllegalStateException();
          }
        }
        case NEWS -> {
          if (channel instanceof NewsChannel newsChannel) {
            channelDefinitions.add(AnnouncementChannelDefinition.from(newsChannel));
          } else {
            throw new IllegalStateException();
          }
        }
        case STAGE -> {
          if (channel instanceof StageChannel stageChannel) {
            channelDefinitions.add(StageChannelDefinition.from(stageChannel));
          } else {
            throw new IllegalStateException();
          }
        }
        case GUILD_NEWS_THREAD, GUILD_PUBLIC_THREAD, GUILD_PRIVATE_THREAD -> {
          log.trace("Got a channel of {} type, but Terra does not deal with threads. {}",
              channel.getType(), channel);
        }
        case FORUM -> {
          if (channel instanceof ForumChannel forumChannel) {
            channelDefinitions.add(ForumChannelDefinition.from(forumChannel));
          } else {
            throw new IllegalStateException();
          }
        }
        case MEDIA -> {
          if (channel instanceof MediaChannel mediaChannel) {
            channelDefinitions.add(MediaChannelDefinition.from(mediaChannel));
          } else {
            throw new IllegalStateException();
          }
        }
        case UNKNOWN -> {
          log.warn("Got a channel of UNKNOWN type: {}", channel);
        }
      }
    }

    var builder = new MessageCreateBuilder();
    return builder.addContent(channelDefinitions.toString()).build();
  }

  @Override
  public String getCommandName() {
    return COMMAND_NAME;
  }

  @Override
  public boolean isEphemeral() {
    return false;
  }
}
