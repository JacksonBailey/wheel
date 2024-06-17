package dev.jacksonbailey.wheel.terra;

import java.util.Collection;
import java.util.EnumSet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class TerraConfig {

  private static final Logger log = LoggerFactory.getLogger(TerraConfig.class);

  @Bean
  @Scope("prototype")
  public JDABuilder jdaBuilder(TerraConfigProps terraConfigProps,
      ObjectProvider<EventListener> listenerProvider) {
    JDABuilder builder = JDABuilder.createDefault(terraConfigProps.token());
    listenerProvider.orderedStream().forEach(builder::addEventListeners);
    builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
    return builder;
  }

  @Bean
  public Collection<Permission> requiredPermissions() {
    return EnumSet.of(Permission.MANAGE_CHANNEL);
  }

  @Bean
  @Profile("live")
  public JDA jda(JDABuilder jdaBuilder, Collection<Permission> requiredPermissions) {
    JDA jda = jdaBuilder.build();
    log.info("Invite URL: {}", jda.getInviteUrl(requiredPermissions));
    return jda;
  }

}
