package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import net.dv8tion.jda.api.entities.channel.ChannelType;

public interface ChannelDefinition {

  String name();

  ChannelType type();

  Integer position();

  List<Overwrite> permissionOverwrites();

}
