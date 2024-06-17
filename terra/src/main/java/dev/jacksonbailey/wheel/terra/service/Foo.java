package dev.jacksonbailey.wheel.terra.service;

import static java.util.Objects.requireNonNull;

import dev.jacksonbailey.wheel.terra.model.JDAReadyEventAdapter;
import java.util.List;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("live")
public class Foo {

  private static final Logger log = LoggerFactory.getLogger(Foo.class);

  private final JDA jda;

  private final long targetGuildId;

  private final ApplicationContext applicationContext;

  public Foo(JDA jda,
      @Value("${bot.target-guild-id}") long targetGuildId,
      ApplicationContext applicationContext) {
    this.jda = jda;
    this.targetGuildId = targetGuildId;
    this.applicationContext = applicationContext;
  }


  @EventListener(JDAReadyEventAdapter.class)
  public void doStuff() throws InterruptedException {
    log.info("Here we go!");
    Guild guild = getGuild();
    var textChannel = guild.createTextChannel("foo").complete();
    log.info("Made {}", textChannel);
    listChannels(guild);
    textChannel.delete().onSuccess(v -> log.info("Deleted {}", textChannel)).complete();
    getGuild();
    SpringApplication.exit(applicationContext, () -> 1);
  }

  private Guild getGuild() {
    var guild = requireNonNull(jda.getGuildById(targetGuildId),
        "No such connected guild id " + targetGuildId);
    log.info(String.valueOf(guild));
    listChannels(guild);
    return guild;
  }

  private static void listChannels(Guild guild) {
    log.info("Channels are...");
    List<GuildChannel> channels = guild.getChannels();
    for (GuildChannel channel : channels) {
      log.info(String.valueOf(channel));
    }
    log.info("...done");
  }

}
