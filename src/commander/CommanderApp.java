package commander;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;

public class CommanderApp {

    public static void main(String[] args) {
        CommanderApp app = new CommanderApp();
        app.run(args);
    }

    public void run(String[] args) {
        Commander commander = CommanderBuilder
                .create()
                .withCommand(Command.CLEAR.command, ClearProcessor::new)
                .withCommand(Command.EXIT.command, ExitProcessor::new)
                .withCommand(Command.HELP.command, () -> param -> Arrays
                        .stream(Command.values())
                        .toList()
                        .stream()
                        .forEach(entry -> println(entry.command)))
                .withCommand(Command.PRINT.command, PrintProcessor::new)
                .withCommand(Command.SUM.command, SumProcessor::new)
                .withReader(new BufferedReader(new StringReader(String.join("\n", args))))
                .build();

        commander.readLoop();
    }

    private static void println(String output) {
        System.out.println("Commander: " + output);
    }
}
