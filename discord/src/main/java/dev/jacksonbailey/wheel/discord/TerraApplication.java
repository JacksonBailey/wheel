package dev.jacksonbailey.wheel.discord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class TerraApplication {

  public static void main(String[] args) {
    SpringApplication.run(TerraApplication.class, args);
  }

}
