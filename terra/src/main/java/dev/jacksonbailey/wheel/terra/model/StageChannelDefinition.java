package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel;

@Builder(toBuilder = true)
public record StageChannelDefinition(
    String name,
    ChannelType type,
    Integer bitrate,
    Integer userLimit,
    Integer rateLimitPerUser,
    Integer position,
    List<Overwrite> permissionOverwrites,
    String parent,
    Boolean nsfw,
    Region rtcRegion,
    Object videoQualityMode
) implements ChannelDefinition {

  public StageChannelDefinition {
    if (!ChannelType.STAGE.equals(type)) {
      throw new IllegalArgumentException("Expected STAGE, got " + type.name());
    }
  }

  public static StageChannelDefinition from(StageChannel stageChannel) {
    return StageChannelDefinition
        .builder()
        .name(stageChannel.getName())
        .type(stageChannel.getType())
        .bitrate(stageChannel.getBitrate())
        .userLimit(stageChannel.getUserLimit())
        .rateLimitPerUser(null) // TODO Where is this?
        .position(stageChannel.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .parent(
            Optional.ofNullable(stageChannel.getParentCategory())
                    .map(Channel::getName)
                    .orElse(null)
        )
        .nsfw(stageChannel.isNSFW())
        .rtcRegion(stageChannel.getRegion())
        .videoQualityMode(null) // TODO Where is this?
        .build();
  }

}
