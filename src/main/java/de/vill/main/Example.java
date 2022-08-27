package de.vill.main;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.constraint.Constraint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Example {
    public static void main(String[] args) throws IOException {
        //parse uvl model from file (if decomposed all submodels must be in the current working directory)
        {
            Path filePath = Path.of("test.uvl");
            String content = Files.readString(filePath);
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content);
        }

        //parse decomposed uvl model from one directory
        {
            Path filePath = Path.of("test.uvl");
            String content = Files.readString(filePath);
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content, "./submodels");
        }

        //parse decomposed uvl model from with specific paths
        {
            HashMap<String, String> fileLoader = new HashMap<>();
            fileLoader.put("util", "./util.uvl");
            fileLoader.put("submodel1", "./test/test2.uvl");
            Path filePath = Path.of("test.uvl");
            String content = Files.readString(filePath);
            UVLModelFactory uvlModelFactory = new UVLModelFactory();
            FeatureModel featureModel = uvlModelFactory.parse(content, fileLoader);
        }

        //safe a single uvl model (this ignores any submodels)
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            String uvlModel = featureModel.toString();
            Path filePath = Path.of("test.uvl");
            Files.writeString(filePath, uvlModel);
        }

        //safe a decomposed uvl model with all its submodels to individual files
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            Map<String, String> modelList = featureModel.decomposedModelToString();
            for(Map.Entry<String, String> uvlModel : modelList.entrySet()) {
                //safe submodel in current working directory with namespace as name
                Path filePath = Path.of("./" + uvlModel.getKey() + ".uvl");
                Files.writeString(filePath, uvlModel.getValue());
            }
        }

        //create a single uvl representation from a decomposed model and safe it to a single file
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            String uvlModel = featureModel.composedModelToString();
            Path filePath = Path.of("test.uvl");
            Files.writeString(filePath, uvlModel);
        }

        //traverse all features depth first search
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            traverseAllFeatures(featureModel.getRootFeature());
        }

        //get constraints of feature model from constraints section (no constraints from submodels or attribtues)
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            featureModel.getOwnConstraints();
        }

        //get all constraints of feature model and submodels and attribtues
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            featureModel.getConstraints();
        }

        //get attribute from feature
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            Feature feature = featureModel.getFeatureMap().get("featureName");
            Attribute attribute = feature.getAttributes().get("attributeName");
            Object value = attribute.getValue();
        }

        //traverse constraint depth first search
        {
            FeatureModel featureModel = null;
            // ... load feature model ...
            traverseConstraint(featureModel.getOwnConstraints().get(0));
        }
    }

    private static void traverseConstraint(Constraint constraint) {
        for(Constraint subConstraint : constraint.getConstraintSubParts()){
            //... do something with constraint
            traverseConstraint(subConstraint);
        }
    }

    public static void traverseAllFeatures(Feature feature){
        for(Group group : feature.getChildren()){
            for(Feature childFeature : group.getFeatures()){
                //... do something with feature
                //or stop at submodel with if(!childfeature.isSubmodelroot())
                traverseAllFeatures(childFeature);
            }
        }
    }
}
