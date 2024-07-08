package dev.jacksonbailey.wheel.terra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.concurrent.Future;
import java.util.function.Consumer;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public final class TestUtils {

  private TestUtils() {
    throw new AssertionError();
  }

  public static <T> Consumer<T> anyConsumerOf(T t) {
    @SuppressWarnings("unchecked")
    Consumer<T> toReturn = any(Consumer.class);
    return toReturn;
  }

  public static <T> Consumer<T> nullableConsumerOf(T t) {
    @SuppressWarnings("unchecked")
    Consumer<T> toReturn = nullable(Consumer.class);
    return toReturn;
  }

  public static <T> Future<T> anyFutureOf(T t) {
    @SuppressWarnings("unchecked")
    Future<T> toReturn = any(Future.class);
    return toReturn;
  }

  public static TextChannel mockTextChannel(String channelName) {
    var channel = mock(TextChannel.class);
    given(channel.getType()).willReturn(ChannelType.TEXT);
    given(channel.getName()).willReturn(channelName);
    return channel;
  }

  public static VoiceChannel mockVoiceChannel(String channelName) {
    var channel = mock(VoiceChannel.class);
    given(channel.getType()).willReturn(ChannelType.VOICE);
    given(channel.getName()).willReturn(channelName);
    return channel;
  }

}
