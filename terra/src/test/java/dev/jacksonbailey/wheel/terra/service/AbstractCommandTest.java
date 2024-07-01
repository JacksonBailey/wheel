package dev.jacksonbailey.wheel.terra.service;

import static dev.jacksonbailey.wheel.terra.TestUtils.anyConsumerOf;
import static dev.jacksonbailey.wheel.terra.TestUtils.nullableConsumerOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractCommandTest {

  @Mock
  private CommandData commandData;

  @Mock
  private JDA jda;

  @InjectMocks
  private AbstractCommandStub commandStub;

  @Mock
  RestAction<Command> upsertRestAction;

  @Mock
  Consumer<Throwable> upsertCommandFailure;

  private static class AbstractCommandStub extends AbstractCommand {

    private final CommandData commandData;

    private final Consumer<Throwable> upsertCommandFailure;

    public AbstractCommandStub(JDA jda, CommandData commandData,
        Consumer<Throwable> upsertCommandFailure) {
      super(jda);
      this.commandData = commandData;
      this.upsertCommandFailure = upsertCommandFailure;
    }

    @Override
    public CommandData getCommandData() {
      return commandData;
    }

    @Override
    public MessageCreateData doCommand(@NotNull SlashCommandInteractionEvent event) {
      return null;
    }

    @Override
    public String getCommandName() {
      return "stub";
    }

    @Override
    public boolean isEphemeral() {
      return false;
    }

    protected Consumer<? super Throwable> onUpsertCommandFailure() {
      return upsertCommandFailure;
    }
  }

  @Test
  void registerCommand_addsListener() {
    given(jda.upsertCommand(any(CommandData.class))).willReturn(upsertRestAction);

    commandStub.registerCommand();

    verify(jda).addEventListener(commandStub);
  }

  @Test
  void registerCommand_upsertsCommand() {
    given(jda.upsertCommand(any(CommandData.class))).willReturn(upsertRestAction);

    commandStub.registerCommand();

    verify(jda).upsertCommand(commandData);
    verify(upsertRestAction).queue(anyConsumerOf(Command.class), eq(upsertCommandFailure));
  }

  @Test
  void onSlashCommand_doesNotTriggersOnWrongCommand() {
    var event = mock(SlashCommandInteractionEvent.class);
    given(event.getName()).willReturn(UUID.randomUUID().toString());

    commandStub.onSlashCommandInteraction(event);

    verify(event).getName();
    verifyNoMoreInteractions(event);
  }

  @Test
  void onSlashCommand_defersReply() {
    var event = mock(SlashCommandInteractionEvent.class);
    var callback = mock(ReplyCallbackAction.class);
    given(event.getName()).willReturn(commandStub.getCommandName());
    given(event.deferReply(anyBoolean())).willReturn(callback);

    commandStub.onSlashCommandInteraction(event);

    verify(event).getName();
    verify(event).deferReply(commandStub.isEphemeral());
    verify(callback).queue(anyConsumerOf(InteractionHook.class),
        nullableConsumerOf(Throwable.class));
  }

  @ParameterizedTest
  @ValueSource(classes = {ExecutionException.class, InterruptedException.class})
  void onDeferReplySuccess_throwsWhenMessageFutureFails(Class<? extends Throwable> thrown)
      throws Exception {
    InteractionHook hook = mock(InteractionHook.class);
    Throwable thrownByMessageFuture = mock(thrown);
    @SuppressWarnings("unchecked")
    Future<MessageCreateData> messageFuture = mock(Future.class);
    given(messageFuture.get()).willThrow(thrownByMessageFuture);

    var actualException = assertThrows(RuntimeException.class,
        () -> commandStub.onDeferReplySuccess(hook, messageFuture));

    assertThat(actualException).cause().isSameAs(thrownByMessageFuture);
  }

  @Test
  void onDeferReplySuccess_hookIsCalled() throws Exception {
    @SuppressWarnings("unchecked")
    Future<MessageCreateData> messageFuture = mock(Future.class);
    var messageCreateData = mock(MessageCreateData.class);
    given(messageFuture.get()).willReturn(messageCreateData);

    var hook = mock(InteractionHook.class);
    @SuppressWarnings("unchecked")
    WebhookMessageCreateAction<Message> callback = mock(WebhookMessageCreateAction.class);
    given(hook.sendMessage(messageCreateData)).willReturn(callback);

    commandStub.onDeferReplySuccess(hook, messageFuture);

    verify(hook).sendMessage(messageCreateData);
    verify(callback).queue(anyConsumerOf(Message.class), nullableConsumerOf(Throwable.class));
  }

}
