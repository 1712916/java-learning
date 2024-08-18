package commander;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Optional;
import java.util.function.Supplier;

class CommanderTest {
    @Test void testCreateCommander() {
        Commander commander = CommanderBuilder.create().build();
        Assertions.assertNotNull(commander);
    }

    @Test void testReadLoop() {
        Commander commander = CommanderBuilder.create().build();
        commander.setReader(new BufferedReader(new StringReader("exit\n")));

        commander.addNewAction("exit", ExitProcessor::new);

        Assertions.assertDoesNotThrow(() -> {
            commander.readLoop();
        });
    }


    @Test void testGetProcessor() {
        Commander commander = CommanderBuilder.create().build();

        Optional<Supplier<Processor>> result = commander.getProcessor("command");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test void testAddNewAction_With_NullAction() {
        Commander commander = CommanderBuilder.create().build();
        commander.addNewAction("command", null);
        Assertions.assertTrue(commander.getProcessor("command").isEmpty());
    }

    @Test void testAddNewCommand() {
        Commander commander = CommanderBuilder.create().build();
        commander.addNewAction("command", () -> new Processor() {
            @Override public void execute(String param) {
            }
        });
        Assertions.assertTrue(commander.getProcessor("command").isPresent());
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme