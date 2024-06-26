package dev.jacksonbailey.wheel.terra.service;

import static dev.jacksonbailey.wheel.terra.service.EchoListener.ECHO_COMMAND_NAME;
import static dev.jacksonbailey.wheel.terra.service.EchoListener.ECHO_PARAM_NAME;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Profile("live")
public class Foo {

  private static final Logger log = LoggerFactory.getLogger(Foo.class);

  private final JDA jda;

  public Foo(JDA jda) {
    this.jda = jda;
  }

  @Async
  @EventListener(ApplicationReadyEvent.class)
  public void doStuff() {
    log.info("Here we go!");
    jda.upsertCommand(
        Commands.slash(ECHO_COMMAND_NAME, "Says it right back")
                .addOption(OptionType.STRING, ECHO_PARAM_NAME, "The thing to say")
    ).queue();
  }

}
