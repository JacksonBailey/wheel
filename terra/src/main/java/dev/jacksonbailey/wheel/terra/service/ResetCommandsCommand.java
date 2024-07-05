package dev.jacksonbailey.wheel.terra.service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

public class ResetCommandsCommand extends AbstractCommand {

  public ResetCommandsCommand(JDA jda) {
    super(jda);
  }

  @Override
  public CommandData getCommandData() {
    return Commands.slash(getCommandName(), "Deletes all registered commands.");
  }

  @Override
  public MessageCreateData doCommand(@NotNull SlashCommandInteractionEvent event) {
    jda.retrieveCommands().queue(commands ->
        commands.parallelStream().forEach(
            command -> jda.deleteCommandById(command.getIdLong()).queue()
        )
    );
    var builder = new MessageCreateBuilder();
    return builder.addContent("Queued for deletion!").build();
  }

  @Override
  public String getCommandName() {
    return "reset-commands";
  }

  @Override
  public boolean isEphemeral() {
    return true;
  }
}
