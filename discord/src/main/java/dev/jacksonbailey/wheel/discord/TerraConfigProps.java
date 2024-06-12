package dev.jacksonbailey.wheel.discord;

import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bot")
public record TerraConfigProps(
    String applicationId,
    URI oauth,
    String token
) {

}
