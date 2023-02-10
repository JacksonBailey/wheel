package dev.jacksonbailey.wheel.vexillum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlagService {

  private static final Logger log = LoggerFactory.getLogger(FlagService.class);

  private final FlagStore flagStore;

  public FlagService(FlagStore flagStore) {
    this.flagStore = flagStore;
  }

  public boolean getFlag(String flagName, boolean defaultValue) {
    log.debug("Getting flag '{}'", flagName);
    boolean value = flagStore.retrieveFlag(flagName).orElse(defaultValue);
    log.debug("Value for flag '{}' is '{}'", flagName, value);
    return value;
  }

}
