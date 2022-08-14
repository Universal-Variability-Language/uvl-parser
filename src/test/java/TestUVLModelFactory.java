import de.vill.config.Configuration;
import de.vill.main.UVLModelFactory;
import de.vill.model.FeatureModel;
import de.vill.model.Import;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestUVLModelFactory {

    @Test
    public void testParseComposedModel() {
        try {
            Configuration.setNewlineSymbol("\n");
            Configuration.setTabulatorSymbol("\t");
            Path filePath = Path.of("./src/test/resources/composition_root.uvl");
            String content = Files.readString(filePath);
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content, "./src/test/resources/");
            var uvlStrings = featureModel.decomposedModelToString();
            for(Map.Entry<String, String> entry : uvlStrings.entrySet()){
                filePath = Path.of("./src/test/resources/"+ entry.getKey() + ".uvl");
                content = Files.readString(filePath);
                assertEquals(entry.getValue(), content);
            }

            //assertEquals(content, featureModel.composedModelToString());
        }catch (IOException e){
            fail();
        }
    }

    @Test
    public void testPrintSingleModel() {
        try {
            Configuration.setNewlineSymbol("\n");
            Configuration.setTabulatorSymbol("\t");
            Path filePath = Path.of("./src/test/resources/composition_sub1.uvl");
            String content = Files.readString(filePath);
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content, "./src/test/resources/");
            var uvlStrings = featureModel.decomposedModelToString();

            for(Import importLine : featureModel.getImports()){
                if(importLine.getFeatureModel().getNamespace().equals("composition_sub1")){
                    assertEquals(importLine.getFeatureModel().toString(), content);
                }
                break;
            }

        }catch (IOException e){
            fail();
        }
    }
}
