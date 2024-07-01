package dev.jacksonbailey.wheel.terra.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

@Builder(toBuilder = true)
public record VoiceChannelDefinition(
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

  public VoiceChannelDefinition {
    if (!ChannelType.VOICE.equals(type)) {
      throw new IllegalArgumentException("Expected VOICE, got " + type.name());
    }
  }

  public static VoiceChannelDefinition from(VoiceChannel voiceChannel) {
    return VoiceChannelDefinition
        .builder()
        .name(voiceChannel.getName())
        .type(voiceChannel.getType())
        .bitrate(voiceChannel.getBitrate())
        .userLimit(voiceChannel.getUserLimit())
        .rateLimitPerUser(null) // TODO Where is this?
        .position(voiceChannel.getPosition())
        .permissionOverwrites(null) // TODO Too lazy right now
        .parent(
            Optional.ofNullable(voiceChannel.getParentCategory())
                    .map(Channel::getName)
                    .orElse(null)
        )
        .nsfw(voiceChannel.isNSFW())
        .rtcRegion(voiceChannel.getRegion())
        .videoQualityMode(null) // TODO Where is this?
        .build();
  }

}
