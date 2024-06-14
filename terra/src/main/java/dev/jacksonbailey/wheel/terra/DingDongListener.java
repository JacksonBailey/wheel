package dev.jacksonbailey.wheel.terra;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class DingDongListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    Message message = event.getMessage();
    String content = message.getContentRaw();
    if (content.equals("!ding")) {
      MessageChannel channel = event.getChannel();
      channel.sendMessage("Dong!").queue();
    }
  }

}
