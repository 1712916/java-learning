package commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Commander {

    private Map<String, Supplier<Processor>> commandAction;

    private BufferedReader reader;

      Commander(
            Map<String, Supplier<Processor>> commandAction,
            BufferedReader reader
    ) {
        this.commandAction = commandAction;
        this.reader = reader;
    }


    public static void main(String[] args) {
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
                .build();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        commander.readLoop();
    }

    private static void println(String output) {
        System.out.println("Commander: " + output);
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Map<String, Supplier<Processor>> getCommandActions() {
        return commandAction;
    }

    void setCommandActions(Map<String, Supplier<Processor>> commandAction) {
        this.commandAction = commandAction;
    }

    public void readLoop() {
        while (true) {
            System.out.print("input: ");
            try {
                String name    = reader.readLine();
                String command = name.split(" ")[0];

                getProcessor(command)
                        .map(Supplier::get)
                        .ifPresentOrElse(processor -> processor.execute(name.replaceFirst(command, "").trim()), () -> {
                            println("Command not found");
                            println("Use help to see available commands");
                        });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExitCommandException e) {
                break;
            }
        }
    }

    public Optional<Supplier<Processor>> getProcessor(String command) {
        return Optional.ofNullable(commandAction.get(command));
    }

    void addNewAction(String command, Supplier<Processor> action) {
        if (commandAction == null) {
            commandAction = new HashMap<String, Supplier<Processor>>();
        }

        commandAction.put(command, action);
    }
}
