package dev.jacksonbailey.wheel.terra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EchoCommandTest {

  @Mock
  private JDA jda;

  @InjectMocks
  private EchoCommand echoCommand;

  @Test
  void echoReturnsInput() {
    var event = mock(SlashCommandInteractionEvent.class);
    var optionMapping = mock(OptionMapping.class);
    given(event.getOption(EchoCommand.ECHO_PARAM_NAME)).willReturn(optionMapping);
    var input = "Hello, World!";
    given(optionMapping.getAsString()).willReturn(input);

    try (var messageCreateData = echoCommand.doCommand(event)) {
      assertEquals(input, messageCreateData.getContent());
    }
  }

}
