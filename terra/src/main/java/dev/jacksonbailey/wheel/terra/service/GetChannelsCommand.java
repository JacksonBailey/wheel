package dev.jacksonbailey.wheel.terra.service;

import dev.jacksonbailey.wheel.terra.model.ChannelDefinition;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
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

    var channelDefinitions = sourceGuild.getChannels().stream()
                                        .map(ChannelDefinition::from)
                                        .toList();

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
