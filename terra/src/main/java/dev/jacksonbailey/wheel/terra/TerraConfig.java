package dev.jacksonbailey.wheel.terra;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TerraConfig {

  @Bean
  @Profile("live")
  public JDA jda(TerraConfigProps terraConfigProps) {
    return JDABuilder.createDefault(terraConfigProps.token())
                     .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                     .build();
  }

}
