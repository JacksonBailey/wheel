package dev.jacksonbailey.wheel.terra.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TerraTestConfig {

  @Bean
  @Profile("!live")
  public JDA jda() {
    JDA jda = mock(JDA.class);
    @SuppressWarnings("unchecked")
    RestAction<Command> restAction = mock(RestAction.class);
    given(jda.upsertCommand(any(CommandData.class))).willReturn(restAction);
    return jda;
  }

}
