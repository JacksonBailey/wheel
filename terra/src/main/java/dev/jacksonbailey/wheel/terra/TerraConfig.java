package dev.jacksonbailey.wheel.terra;

import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TerraConfig {

  @Bean
  public JDABuilder jdaBuilder(TerraConfigProps terraConfigProps) {
    return JDABuilder.createDefault(terraConfigProps.token());
  }

}
