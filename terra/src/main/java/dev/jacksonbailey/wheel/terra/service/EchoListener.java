package dev.jacksonbailey.wheel.terra.service;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;

@Service
public class EchoListener extends ListenerAdapter {

  public static final String ECHO_COMMAND_NAME = "echo";
  public static final String ECHO_PARAM_NAME = "message";

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    if (ECHO_COMMAND_NAME.equals(event.getName())) {
      event.deferReply().queue();
      event.getHook().sendMessage(event.getOption(ECHO_PARAM_NAME).getAsString()).queue();
    }
  }

}
