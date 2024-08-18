package commander;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommanderAppTest {

    @Test public void testRun_withValidCommands() throws Exception {
        CommanderApp app = new CommanderApp();
        Assertions.assertDoesNotThrow(() -> {
            app.main(new String[]{"print", "exit"});
        });
    }
}