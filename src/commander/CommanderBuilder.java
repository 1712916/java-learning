package commander;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class CommanderBuilder {
    private final Map<String, Supplier<Processor>> commandAction = new HashMap<>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static CommanderBuilder create() {
        return new CommanderBuilder();
    }

    public CommanderBuilder withCommand(String command, Supplier<Processor> processorSupplier) {
        commandAction.put(command, processorSupplier);
        return this;
    }

    public CommanderBuilder withReader(BufferedReader reader) {
        this.reader = reader;
        return this;
    }

    public Commander build() {
        try {
            Commander commander = new Commander(commandAction, getReader().orElseThrow());
            return commander;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to build commander", e);
        }
    }

    private Optional<BufferedReader> getReader() {
        return Optional.ofNullable(reader);
    }
}