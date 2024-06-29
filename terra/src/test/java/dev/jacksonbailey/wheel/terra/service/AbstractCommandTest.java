package dev.jacksonbailey.wheel.terra.service;

import static dev.jacksonbailey.wheel.terra.TestUtils.anyConsumerOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
  private AbstractCommandStub abstractCommandStub;

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

  @BeforeEach
  void beforeEach() {
    given(jda.upsertCommand(any(CommandData.class))).willReturn(upsertRestAction);
  }

  @Test
  void registerCommandAddsListener() {
    abstractCommandStub.registerCommand();
    verify(jda).addEventListener(abstractCommandStub);
  }

  @Test
  void registerCommandUpsertsCommand() {
    abstractCommandStub.registerCommand();
    verify(jda).upsertCommand(commandData);
    verify(upsertRestAction).queue(anyConsumerOf(Command.class), eq(upsertCommandFailure));
  }

  // TODO Add more unit tests
}
