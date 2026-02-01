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
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UVLParserTest {

    private static final Path TEST_MODELS_DIR = Paths.get("../test_models");

    // Files misplaced in test_models/ that actually test invalid syntax
    // (dotinfeaturename.uvl has comment "should be illegal" - dots not allowed in quoted IDs)
    private static final Set<String> MISPLACED_INVALID_FILES = Set.of(
            "dotinfeaturename.uvl"
    );

    // Files with known Java lexer edge cases (block comments with tabs)
    private static final Set<String> JAVA_LEXER_EDGE_CASES = Set.of(
            "fm03_block_comment.uvl"
    );

    // Semantic errors that the parser cannot detect (only syntax errors are caught)
    private static final Set<String> SEMANTIC_ERROR_FILES = Set.of(
            "missingreference.uvl",
            "wrong_attribute_name.uvl",
            "same_feature_names.uvl"
    );

    static Stream<Path> validModels() throws IOException {
        return Files.walk(TEST_MODELS_DIR)
                .filter(p -> p.toString().endsWith(".uvl"))
                .filter(p -> !p.toString().contains("faulty"))
                .filter(p -> !MISPLACED_INVALID_FILES.contains(p.getFileName().toString()))
                .filter(p -> !JAVA_LEXER_EDGE_CASES.contains(p.getFileName().toString()));
    }

    static Stream<Path> faultyModels() throws IOException {
        return Files.list(TEST_MODELS_DIR.resolve("faulty"))
                .filter(p -> p.toString().endsWith(".uvl"))
                .filter(p -> !SEMANTIC_ERROR_FILES.contains(p.getFileName().toString()));
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
