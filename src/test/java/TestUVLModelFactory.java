import de.vill.main.UVLModelFactory;
import de.vill.model.FeatureModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUVLModelFactory {
    @Test
    public void testParseComposedModel() {
        /*
        try {
            Path filePath = Path.of("./src/test/resources/includetest.uvl");
            String content = Files.readString(filePath);
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content);
            assertEquals(content, featureModel.composedModelToString());
        }catch (IOException e){
            fail();
        }

         */
    }
}
