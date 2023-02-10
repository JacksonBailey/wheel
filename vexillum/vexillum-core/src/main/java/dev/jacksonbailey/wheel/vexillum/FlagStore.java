package dev.jacksonbailey.wheel.vexillum;

import java.util.Optional;

public interface FlagStore {

  Optional<Boolean> retrieveFlag(String flagName);

}
