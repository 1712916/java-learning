package commander;

public enum Command {
    CLEAR("clear"), EXIT("exit"), HELP("help"), PRINT("print"), SUM("sum");

    public final String command;

    Command(String command) {
        this.command = command;
    }

}