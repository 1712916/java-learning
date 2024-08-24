package commander;

public interface Processor {
    void execute(String param);
}


class ClearProcessor implements Processor {
    public ClearProcessor() {}

    @Override
    public void execute(String param) {
        System.out.println("Clearing screen...");
    }
}

class ExitCommandException extends RuntimeException {
    public ExitCommandException() {
        super("Exiting...");
    }
}


class PrintProcessor implements Processor {
    public PrintProcessor() {}

    @Override
    public void execute(String param) {
        System.out.println(param);
    }
}

class SumProcessor implements Processor {
    public SumProcessor() {}

    @Override
    public void execute(String param) {
        System.out.println("Summing...");
        try {
            final String numStr1 = param.split(" ")[0];
            final String numStr2 = param.split(" ")[1];
            final double num1 = Double.parseDouble(numStr1);
            final double num2 = Double.parseDouble(numStr2);

            System.out.println(num1 + num2);
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}