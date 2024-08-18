package commander;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ProcessorTest {

    @Test void clearProcessorShouldClearScreen() {
        ClearProcessor        clearProcessor = new ClearProcessor();
        ByteArrayOutputStream outContent     = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        clearProcessor.execute("");
        assertEquals("Clearing screen...\n", outContent.toString());
    }

    @Test void exitProcessorShouldExit() {
        ExitProcessor exitProcessor = new ExitProcessor();
        assertThrows(ExitCommandException.class, () -> exitProcessor.execute(""));
    }

    @Test void printProcessorShouldPrintParam() {
        PrintProcessor        printProcessor = new PrintProcessor();
        ByteArrayOutputStream outContent     = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        printProcessor.execute("test");
        assertEquals("test\n", outContent.toString());
    }

    @Test void sumProcessorShouldSumNumbers() {
        SumProcessor          sumProcessor = new SumProcessor();
        ByteArrayOutputStream outContent   = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        sumProcessor.execute("5 10");
        assertEquals("Summing...\n15.0\n", outContent.toString());
    }

    @Test void sumProcessorShouldHandleInvalidInput() {
        SumProcessor          sumProcessor = new SumProcessor();
        ByteArrayOutputStream outContent   = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        sumProcessor.execute("invalid input");
        assertEquals("Summing...\nInvalid input\n", outContent.toString());
    }
}

