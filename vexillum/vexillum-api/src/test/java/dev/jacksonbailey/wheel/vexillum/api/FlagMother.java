package dev.jacksonbailey.wheel.vexillum.api;

public class FlagMother {

  public static Flag makeFlag(String name, Boolean state) {
    var flagBuilder = Flag.newBuilder();
    if (name != null) {
      flagBuilder.setName(name);
    }
    if (state != null) {
      flagBuilder.setState(state);
    }
    return flagBuilder.build();
  }

}
