package dev.jacksonbailey.wheel.terra.config;

import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.JDA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TerraTestConfig {

  @Bean
  @Profile("!live")
  public JDA jda() {
    return mock(JDA.class);
  }

}
