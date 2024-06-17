package dev.jacksonbailey.wheel.terra;

import net.dv8tion.jda.api.events.session.GenericSessionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.SessionDisconnectEvent;
import net.dv8tion.jda.api.events.session.SessionInvalidateEvent;
import net.dv8tion.jda.api.events.session.SessionRecreateEvent;
import net.dv8tion.jda.api.events.session.SessionResumeEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publishes JDA Session events to Spring
 */
@Component
public class JDASessionEventPublisher extends ListenerAdapter {

  private final ApplicationEventPublisher publisher;

  public JDASessionEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  private void doPublish(@NotNull GenericSessionEvent event) {
    publisher.publishEvent(new JDAEventAdapter(this, event));
  }

  @Override
  public void onReady(@NotNull ReadyEvent event) {
    doPublish(event);
  }

  @Override
  public void onSessionInvalidate(@NotNull SessionInvalidateEvent event) {
    doPublish(event);
  }

  @Override
  public void onSessionDisconnect(@NotNull SessionDisconnectEvent event) {
    doPublish(event);
  }

  @Override
  public void onSessionResume(@NotNull SessionResumeEvent event) {
    doPublish(event);
  }

  @Override
  public void onSessionRecreate(@NotNull SessionRecreateEvent event) {
    doPublish(event);
  }

  @Override
  public void onShutdown(@NotNull ShutdownEvent event) {
    doPublish(event);
  }
}
