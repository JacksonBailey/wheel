package dev.jacksonbailey.wheel.terra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class TerraApplication {

  public static void main(String[] args) {
    SpringApplication.run(TerraApplication.class, args);
  }

}
