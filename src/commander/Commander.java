package commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class Commander {
    static final String EXIT = "exit";
    static final String CLEAR = "clear";
    static final String HELP = "help";
    static final String PRINT = "print";
    static final String SUM = "sum";
    private final Map<String, Function<Object, Processor>> commandAction = new HashMap<>();

    private Commander() {
    }

    public static Commander createCommander() {
        Commander commander = new Commander();
        commander.initActions();
        return commander;
    }

    void initActions() {
        commandAction.put(CLEAR, (o) -> new ClearProcessor());
        commandAction.put(EXIT, (o) -> new ExitProcessor());
        commandAction.put(HELP, (o) -> (Processor) param -> commandAction.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> println(entry.getKey())));
        commandAction.put(PRINT, (o) -> new PrintProcessor());
        commandAction.put(SUM, (o) -> new SumProcessor());
    }

    public void clearScreen() {
        System.out.println("TODO: Clear screen");

    }

    public void readLoop() throws IOException {
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                // Reading data using readLine
                String name = reader.readLine();
                String command = name.split(" ")[0];

                if (commandAction.containsKey(command)) {
                    final Processor process = commandAction.get(command).apply(0);

                    //get all string after the command
                    final String args = name.replaceFirst(command, "").trim();

                    process.execute(args);
                } else {
                    println("Command not found");
                    println("Use help to see available commands");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExitCommandException e) {
                break;
            }
        }
    }

    private void println(String output) {
        System.out.println("Commander: " + output);
    }

}
