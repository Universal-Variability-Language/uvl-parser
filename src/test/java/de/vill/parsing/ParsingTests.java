package de.vill.parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.vill.main.UVLModelFactory;
import de.vill.model.FeatureModel;

public class ParsingTests {

    // Boolean level models
    private static final String SIMPLE_BOOLEAN = "src/test/resources/parsing/boolean.uvl";
    private static final String NAMESPACE = "src/test/resources/parsing/namespace.uvl";
    private static final String ATTRIBUTES = "src/test/resources/parsing/namespace.uvl";
    private static final String GROUP_CARDINALITY = "src/test/resources/parsing/cardinality.uvl";

    // Arithmetic level models
    private static final String ARITHMETIC_SIMPLE_CONSTRAINTS = "src/test/resources/parsing/arithmetic-simpleconstraints.uvl";
    private static final String AGGREGATE = "src/test/resources/parsing/aggregate.uvl";
    private static final String FEATURE_CARDINALITY = "src/test/resources/parsing/feature-cardinality.uvl";

    // Type level models
    private static final String TYPE = "src/test/resources/parsing/arithmetic-simpleconstraints.uvl";
    private static final String STING_CONSTRAINTS = "src/test/resources/parsing/string-constraints.uvl";

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

    private void testModelParsing(String path) throws IOException {
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        String content = new String(Files.readAllBytes(Paths.get(path)));
        FeatureModel result = uvlModelFactory.parse(content);
        assert (result != null);
    }

}
