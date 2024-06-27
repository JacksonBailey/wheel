package dev.jacksonbailey.wheel.terra.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommand extends ListenerAdapter {

  private static final Logger log = LoggerFactory.getLogger(AbstractCommand.class);

  public abstract MessageCreateData doCommand(@NotNull SlashCommandInteractionEvent event);

  public abstract String getCommandName();

  public abstract boolean isEphemeral();

  @Override
  public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    if (event.getName().equals(getCommandName())) {
      var messageFuture = CompletableFuture.supplyAsync(() -> doCommand(event));
      event.deferReply(isEphemeral())
           .queue(hook -> onDeferReplySuccess(hook, messageFuture), onDeferReplyFailure());
    }
  }

  protected void onDeferReplySuccess(InteractionHook hook,
      Future<MessageCreateData> messageFuture) {
    MessageCreateData messageCreateData;
    try {
      messageCreateData = messageFuture.get();
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    hook.sendMessage(messageCreateData)
        .queue(this::onDeferredReplySuccess, onDeferredReplyFailure());
  }

  // It seems like JDA logs errors by default
  protected Consumer<? super Throwable> onDeferReplyFailure() {
    return null;
  }

  protected void onDeferredReplySuccess(Message message) {

  }

  // It seems like JDA logs errors by default
  protected Consumer<? super Throwable> onDeferredReplyFailure() {
    return null;
  }
}
