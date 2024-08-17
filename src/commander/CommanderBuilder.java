package commander;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommanderBuilder {
    private final Map<String, Supplier<Processor>> commandAction = new HashMap<>();

    public static CommanderBuilder create() {
        return new CommanderBuilder();
    }

    public CommanderBuilder withCommand(String command, Supplier<Processor> processorSupplier) {
        commandAction.put(command, processorSupplier);
        return this;
    }

    public Commander build() {
        Commander commander = Commander.createCommander();
        commander.setCommandActions(commandAction);
        return commander;
    }
}