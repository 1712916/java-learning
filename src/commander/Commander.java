package commander;

import java.io.BufferedReader;
import java.io.IOException;
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
        if (commandAction.isEmpty()) {
            println("No commands available");

            return;
        }

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

