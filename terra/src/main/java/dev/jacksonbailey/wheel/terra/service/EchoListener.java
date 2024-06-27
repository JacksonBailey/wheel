package dev.jacksonbailey.wheel.terra.service;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EchoListener extends AbstractCommand {

  public static final String ECHO_COMMAND_NAME = "echo";
  public static final String ECHO_PARAM_NAME = "message";

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
