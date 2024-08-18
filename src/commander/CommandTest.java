package commander;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    void commandShouldReturnCorrectString() {
        Assertions.assertEquals("clear", Command
                .CLEAR.command);
        Assertions.assertEquals("exit", Command.EXIT.command);
        Assertions.assertEquals("help", Command.HELP.command);
        Assertions.assertEquals("print", Command.PRINT.command);
        Assertions.assertEquals("sum", Command.SUM.command);
    }

    @Test
    void commandShouldNotReturnIncorrectString() {
        Assertions.assertNotEquals("incorrect", Command.CLEAR.command);
        Assertions.assertNotEquals("incorrect", Command.EXIT.command);
        Assertions.assertNotEquals("incorrect", Command.HELP.command);
        Assertions.assertNotEquals("incorrect", Command.PRINT.command);
        Assertions.assertNotEquals("incorrect", Command.SUM.command);
    }
}