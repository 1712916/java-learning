package commander;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommanderBuilderTest {

    @Test void testCreate() {
        CommanderBuilder result = CommanderBuilder.create();
        Assertions.assertNotNull(result);
    }

    @Test void testWithCommand_With_NullCommands() {
        Assertions.assertDoesNotThrow(() -> {
            CommanderBuilder.create().withCommand("command", null);
        });
    }

    @Test void testWithReader_With_NullReader() {
        CommanderBuilder result  = CommanderBuilder.create();
        CommanderBuilder result1 = result.withReader(null);
        Assertions.assertNotNull(result);

    }

    @Test void testBuild() {
        CommanderBuilder builder = CommanderBuilder.create().withCommand("command", () -> new Processor() {
            @Override public void execute(String param) {}
        });
        Commander commander = builder.build();
        Assertions.assertNotNull(builder);
        Assertions.assertNotNull(commander);
        Assertions.assertTrue(commander.getProcessor("command").isPresent());
    }

    @Test void testBuild_Null_Commands() {
        CommanderBuilder builder = CommanderBuilder.create().withCommand("command", () -> new Processor() {
            @Override public void execute(String param) {}
        });
        Commander commander = builder.build();
        Assertions.assertNotNull(builder);
        Assertions.assertNotNull(commander);
        Assertions.assertTrue(commander.getProcessor("command").isPresent());
    }

    @Test void testBuild_With_NullReader() {
        CommanderBuilder builder = CommanderBuilder.create().withReader(null);

        Assertions.assertThrowsExactly(IllegalStateException.class, () -> {
            builder.build();
        });
    }
}

