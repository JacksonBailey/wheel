package dev.jacksonbailey.wheel.terra;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PingPong extends ListenerAdapter {

  private static final Logger log = LoggerFactory.getLogger(PingPong.class);

  private final JDA jda;

  public PingPong(JDA jda) {
    this.jda = jda;
    // TODO This is wrong wrong wrong!
    jda.addEventListener(this);
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) {
      return;
    }
    // We don't want to respond to other bot accounts, including ourself
    Message message = event.getMessage();
    String content = message.getContentRaw();
    // getContentRaw() is an atomic getter
    // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip
    // discord formatting)
    if (content.equals("!ping")) {
      MessageChannel channel = event.getChannel();
      channel.sendMessage("Pong!")
             .queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }
  }

}
