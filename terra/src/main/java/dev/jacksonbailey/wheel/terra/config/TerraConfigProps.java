package dev.jacksonbailey.wheel.terra.config;

import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bot")
public record TerraConfigProps(
    String applicationId,
    long targetGuildId,
    URI oauth,
    String token
) {

}
