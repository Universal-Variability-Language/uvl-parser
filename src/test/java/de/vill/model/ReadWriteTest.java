package de.vill.model;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import de.vill.main.UVLModelFactory;

public class ReadWriteTest {
    
    private final static String TEMP_PATH = "temp.uvl";
    private final static String TEST_MODEL_ONE = "src/test/resources/test1.uvl";

    @Test
    void testReadWrite() throws Exception {
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        
        // Read
        String content = new String(Files.readAllBytes(Paths.get(TEST_MODEL_ONE)));
        FeatureModel featureModel = uvlModelFactory.parse(content);

        // Write
        Files.write(Paths.get(TEMP_PATH), featureModel.toString().getBytes());

        // Try reading again
        content = new String(Files.readAllBytes(Paths.get(TEMP_PATH)));
        uvlModelFactory.parse(content);
        Files.delete(Paths.get(TEMP_PATH));
    }
}
