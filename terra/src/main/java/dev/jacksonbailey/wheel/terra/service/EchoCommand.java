package dev.jacksonbailey.wheel.terra.service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Echos a phrase back to the user. This is really only for troubleshooting.
 */
@Service
public class EchoCommand extends AbstractCommand {

  public static final String ECHO_COMMAND_NAME = "echo";
  public static final String ECHO_PARAM_NAME = "message";

  public EchoCommand(JDA jda) {
    super(jda);
  }

  @Override
  public CommandData getCommandData() {
    return Commands.slash(ECHO_COMMAND_NAME, "Says it right back")
                   .addOption(OptionType.STRING, ECHO_PARAM_NAME, "The thing to say", true);
  }

  @Override
  public MessageCreateData doCommand(@NotNull SlashCommandInteractionEvent event) {
    var builder = new MessageCreateBuilder();
    return builder.addContent(event.getOption(ECHO_PARAM_NAME).getAsString()).build();
  }

  @Override
  public String getCommandName() {
    return ECHO_COMMAND_NAME;
  }

  @Override
  public boolean isEphemeral() {
    return false;
  }

}
