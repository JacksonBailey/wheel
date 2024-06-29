package dev.jacksonbailey.wheel.terra.config;

import java.util.Collection;
import java.util.EnumSet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class TerraConfig {

  private static final Logger log = LoggerFactory.getLogger(TerraConfig.class);

  @Bean
  @Scope("prototype")
  public JDABuilder jdaBuilder(TerraConfigProps terraConfigProps) {
    return JDABuilder.createDefault(terraConfigProps.token());
  }

  @Bean
  public Collection<Permission> requiredPermissions() {
    return EnumSet.of(Permission.MANAGE_CHANNEL);
  }

  @Bean
  @Profile("live")
  public JDA jda(JDABuilder jdaBuilder, Collection<Permission> requiredPermissions,
      @Value("${bot.target-guild-id}") long targetGuildId) throws InterruptedException {
    JDA jda = jdaBuilder.build();
    log.info("Invite URL: {}&guild_id={}", jda.getInviteUrl(requiredPermissions), targetGuildId);
    jda.awaitReady();
    return jda;
  }

}
