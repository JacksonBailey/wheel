package dev.jacksonbailey.wheel.terra;

import java.time.Clock;
import net.dv8tion.jda.api.events.GenericEvent;
import org.springframework.context.ApplicationEvent;

/**
 * Wraps JDA {@link GenericEvent}s for use as Spring {@link ApplicationEvent}s.
 */
public class JDAEventAdapter extends ApplicationEvent {

  private final GenericEvent jdaEvent;

  public JDAEventAdapter(Object source, GenericEvent jdaEvent) {
    super(source);
    this.jdaEvent = jdaEvent;
  }

  public JDAEventAdapter(Object source, Clock clock, GenericEvent jdaEvent) {
    super(source, clock);
    this.jdaEvent = jdaEvent;
  }

  public GenericEvent getJdaEvent() {
    return jdaEvent;
  }

}
