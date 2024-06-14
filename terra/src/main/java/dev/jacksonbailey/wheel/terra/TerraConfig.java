package dev.jacksonbailey.wheel.terra;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class TerraConfig {

  @Bean
  @Scope("prototype")
  public JDABuilder jdaBuilder(TerraConfigProps terraConfigProps,
      ObjectProvider<ListenerAdapter> listenerProvider) {
    JDABuilder builder = JDABuilder.createDefault(terraConfigProps.token());
    listenerProvider.orderedStream().forEach(builder::addEventListeners);
    builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
    return builder;
  }

  @Bean
  @Profile("live")
  public JDA jda(JDABuilder jdaBuilder) {
    return jdaBuilder.build();
  }

}
