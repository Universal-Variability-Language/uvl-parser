package de.vill.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.vill.main.UVLModelFactory;
import de.vill.model.FeatureModel;

public class ParsingTests {

    private static final String TEST_MODEL_PREFIX = "src" + File.separator + "test" + File.separator + "resources"
            + File.separator + "parsing" + File.separator;

    private static final String CONCEPTS_MODEL_PREFIX = TEST_MODEL_PREFIX + File.separator + "concepts"
            + File.separator;
    private static final String FAULTY_MODEL_PREFIX = TEST_MODEL_PREFIX + File.separator + "faulty" + File.separator;
    private static final String EXPLICIT_LANGUAGE_LEVELS_PREFIX = TEST_MODEL_PREFIX + File.separator + "language-levels"
            + File.separator;
    private static final String COMPLEX_MODEL_PREFIX = TEST_MODEL_PREFIX + File.separator + "complex" + File.separator;

    // Boolean level models
    private static final String SIMPLE_BOOLEAN = CONCEPTS_MODEL_PREFIX + "boolean.uvl";
    private static final String NAMESPACE = CONCEPTS_MODEL_PREFIX + "namespace.uvl";
    private static final String ATTRIBUTES = CONCEPTS_MODEL_PREFIX + "namespace.uvl";
    private static final String GROUP_CARDINALITY = CONCEPTS_MODEL_PREFIX + "cardinality.uvl";

    // Arithmetic level models
    private static final String ARITHMETIC_SIMPLE_CONSTRAINTS = CONCEPTS_MODEL_PREFIX
            + "arithmetic-simpleconstraints.uvl";
    private static final String AGGREGATE = CONCEPTS_MODEL_PREFIX + "aggregate.uvl";
    private static final String FEATURE_CARDINALITY = CONCEPTS_MODEL_PREFIX + "feature-cardinality.uvl";

    // Type level models
    private static final String TYPE = CONCEPTS_MODEL_PREFIX + "arithmetic-simpleconstraints.uvl";
    private static final String STING_CONSTRAINTS = CONCEPTS_MODEL_PREFIX + "string-constraints.uvl";

    // Language level models
    private static final String ALL = EXPLICIT_LANGUAGE_LEVELS_PREFIX + "all.uvl";
    private static final String ARITHMETIC = EXPLICIT_LANGUAGE_LEVELS_PREFIX + "arithmetic.uvl";
    private static final String BOOLEAN_LEVEL = EXPLICIT_LANGUAGE_LEVELS_PREFIX + "boolean.uvl";
    private static final String TYPE_LEVEL = EXPLICIT_LANGUAGE_LEVELS_PREFIX + "type.uvl";

    // Faulty UVL models
    private static final String ILLEGAL_NAME = FAULTY_MODEL_PREFIX + "illegalname.uvl";
    private static final String MISSING_REFRENCE = FAULTY_MODEL_PREFIX + "missingreference.uvl";
    private static final String WRONG_INDENT = FAULTY_MODEL_PREFIX + "wrongindent.uvl";

    @Test
    void testBooleanModel() throws Exception {
        testModelParsing(SIMPLE_BOOLEAN);
    }

    @Test
    void testNamespace() throws Exception {
        testModelParsing(NAMESPACE);
    }

    @Test
    void testAttributes() throws Exception {
        testModelParsing(ATTRIBUTES);
    }

    @Test
    void testGroupCardinality() throws Exception {
        testModelParsing(GROUP_CARDINALITY);
    }

    @Test
    void testArithmethicSimpleConstraints() throws Exception {
        testModelParsing(ARITHMETIC_SIMPLE_CONSTRAINTS);
    }

    @Test
    void testAggregate() throws Exception {
        testModelParsing(AGGREGATE);
    }

    @Test
    void testFeatureCardinality() throws Exception {
        testModelParsing(FEATURE_CARDINALITY);
    }

    @Test
    void testType() throws Exception {
        testModelParsing(TYPE);
    }

    @Test
    void testStringConstraints() throws Exception {
        testModelParsing(STING_CONSTRAINTS);
    }

    @Test
    void checkLanguageLevelInclude() throws Exception {
        testModelParsing(ALL);
        testModelParsing(ARITHMETIC);
        testModelParsing(BOOLEAN_LEVEL);
        testModelParsing(TYPE_LEVEL);

    }

    @Test
    void checkComplexModels() throws Exception {
        checkAllModelsInDirectory(COMPLEX_MODEL_PREFIX);
    }

    @Test
    void checkFaultyModels() throws Exception {
        testModelParsing(ILLEGAL_NAME, false);
        testModelParsing(MISSING_REFRENCE, false);
        testModelParsing(WRONG_INDENT, false);

    }

    private void testModelParsing(String path) {
        testModelParsing(path, true);
    }

    public static FeatureModel testModelParsing(String path, boolean expectSuccess) {
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        String content;
        FeatureModel result = null;
        boolean error = false;
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
            result = uvlModelFactory.parse(content);
        } catch (Exception e) {
            error = true;
        }

        boolean actuallySuccess = !error & (result != null);

        assertEquals(expectSuccess, actuallySuccess, "Exception! Failed to assert: " + path);

        return result;
    }

    private void checkAllModelsInDirectory(String dirPath) throws IOException {
        List<File> files = new ArrayList<>();
        getAllModelsInDirectory(dirPath, files);
        for (File file : files) {
            testModelParsing(file.getAbsolutePath());
        }
    }

    public static void getAllModelsInDirectory(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        File[] fileList = directory.listFiles();
        if (fileList != null)
            for (File file : fileList) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".uvl")) {
                        files.add(file);
                    }
                } else if (file.isDirectory()) {
                    getAllModelsInDirectory(file.getAbsolutePath(), files);
                }
            }
    }

}
