package dev.jacksonbailey.wheel.terra.model;

import java.time.Clock;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.springframework.context.ApplicationEvent;

/**
 * Wraps JDA {@link ReadyEvent}s for use as Spring {@link ApplicationEvent}s.
 */
public class JDAReadyEventAdapter extends ApplicationEvent {

  private final GenericEvent jdaEvent;

  public JDAReadyEventAdapter(Object source, ReadyEvent jdaEvent) {
    super(source);
    this.jdaEvent = jdaEvent;
  }

  public JDAReadyEventAdapter(Object source, Clock clock, ReadyEvent jdaEvent) {
    super(source, clock);
    this.jdaEvent = jdaEvent;
  }

  public GenericEvent getJdaEvent() {
    return jdaEvent;
  }

}
