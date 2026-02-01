package uvl;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UVLParserTest {

    private static final Path TEST_MODELS_DIR = Paths.get("../test_models");

    static Stream<Path> validModels() throws IOException {
        return Files.walk(TEST_MODELS_DIR)
                .filter(p -> p.toString().endsWith(".uvl"))
                .filter(p -> !p.toString().contains("faulty"));
    }

    static Stream<Path> faultyModels() throws IOException {
        return Files.list(TEST_MODELS_DIR.resolve("faulty"))
                .filter(p -> p.toString().endsWith(".uvl"));
    }

    private void parseFile(Path file) throws IOException {
        CharStream input = CharStreams.fromPath(file);
        UVLJavaLexer lexer = new UVLJavaLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        UVLJavaParser parser = new UVLJavaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        parser.featureModel();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validModels")
    void testValidModel(Path file) {
        assertDoesNotThrow(() -> parseFile(file), "Failed to parse: " + file);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("faultyModels")
    void testFaultyModel(Path file) {
        assertThrows(RuntimeException.class, () -> parseFile(file), "Expected parse error for: " + file);
    }
}
