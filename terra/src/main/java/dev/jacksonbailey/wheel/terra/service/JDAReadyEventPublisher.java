package dev.jacksonbailey.wheel.terra.service;

import dev.jacksonbailey.wheel.terra.model.JDAReadyEventAdapter;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Publishes JDA Session events to Spring
 */
@Service
public class JDAReadyEventPublisher extends ListenerAdapter {

  private final ApplicationEventPublisher publisher;

  public JDAReadyEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void onReady(@NotNull ReadyEvent event) {
    publisher.publishEvent(new JDAReadyEventAdapter(this, event));
  }
}
