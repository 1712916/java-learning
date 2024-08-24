package commander;

public class ExitProcessor implements Processor {
    public ExitProcessor() {}

    @Override
    public void execute(String param) {
        System.out.println("Exiting...");
        throw new ExitCommandException();
    }
}
