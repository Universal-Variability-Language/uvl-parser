package de.vill.main;

import de.vill.config.Configuration;
import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.constraint.Constraint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Example {
    public static void main(String[] args) throws IOException {
        FeatureModel featureModel = loadUVLFeatureModelFromFile("test.uvl");

        //traverse all features depth first search
        traverseAllFeatures(featureModel.getRootFeature());

        //get constraints of feature model from constraints section (no constraints from submodels or attribtues)
        List<Constraint> ownConstraint = featureModel.getOwnConstraints();

        //traverse a constraint depth first search
        if (ownConstraint.size() > 0) {
            traverseConstraint(ownConstraint.get(0));
        }

        //get all constraints of feature model and submodels and attribtues
        List<Constraint> allConstraint = featureModel.getConstraints();

        //get attribute from feature
        String featureName = "featureName";
        String attributeName = "attributeName";
        Feature feature = featureModel.getFeatureMap().get(featureName);
        if (feature != null) {
            Attribute attribute = feature.getAttributes().get(attributeName);
            if (attribute != null) {
                Object value = attribute.getValue();
                System.out.println("Attribute " + attributeName + " of feature " + featureName + " is: " + value.toString());
            } else {
                System.err.println("Attribute " + attributeName + " in feature " + featureName + " not found!");
            }
        } else {
            System.err.println("Feature " + featureName + " not found!");
        }

        //make feature abstract
        featureName = "featureName";
        feature = featureModel.getFeatureMap().get(featureName);
        if (feature != null) {
            feature.getAttributes().put("abstract", new Attribute<Boolean>("abstract", true));
        } else {
            System.err.println("Feature " + featureName + " not found!");
        }

        //change newline and tabulator symbol for printing
        Configuration.setTabulatorSymbol("        ");
        Configuration.setNewlineSymbol("\n");

        //safe a single uvl model (this ignores any submodels)
        String uvlModel = featureModel.toString();
        Path filePath = Paths.get("test_singleModel.uvl");
        Files.write(filePath, uvlModel.getBytes());

        //safe a decomposed uvl model with all its submodels to individual files
        Map<String, String> modelList = featureModel.decomposedModelToString();
        for (Map.Entry<String, String> uvlSubModel : modelList.entrySet()) {
            //safe submodel in sub directory directory with namespace as name
            Files.createDirectories(Paths.get("./subModels/"));
            filePath = Paths.get("./subModels/" + uvlSubModel.getKey() + ".uvl");
            Files.write(filePath, uvlSubModel.getValue().getBytes());
        }

        //create a single uvl representation from a decomposed model and safe it to a single file
        uvlModel = featureModel.composedModelToString();
        filePath = Paths.get("test_composedModel.uvl");
        Files.write(filePath, uvlModel.getBytes());
    }

    /**
     * Parse uvl model from file (if decomposed all submodels must be in the current working directory)
     *
     * @param path path to the file with uvl model
     * @return the uvl model described in the file
     * @throws IOException for io exceptions while loading the file content
     */
    private static FeatureModel loadUVLFeatureModelFromFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        String content = new String(Files.readAllBytes(filePath));
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        FeatureModel featureModel = uvlModelFactory.parse(content);
        return featureModel;
    }

    /**
     * Parse a decomposed uvl model where all submodels are in a directory and named according to their namespaces.
     *
     * @param rootModelPath Path to the uvl root model file
     * @param subModelDir   Path to the directory with all submodels
     * @return the uvl model described in the file
     * @throws IOException for io exceptions while loading the file content
     */
    private static FeatureModel loadUVLFeatureModelFromDirectory(String rootModelPath, String subModelDir) throws IOException {
        Path filePath = Paths.get(rootModelPath);
        String content = new String(Files.readAllBytes(filePath));
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        FeatureModel featureModel = uvlModelFactory.parse(content, subModelDir);
        return featureModel;
    }

    /**
     * Parse a decomposed uvl model where all submodels locations are specified in a map with namespace as key and path as value.
     *
     * @param rootModelPath Path to the uvl root model file
     * @param subModelPaths Map with submodels with namespace as key and path as value
     * @return the uvl model described in the file
     * @throws IOException for io exceptions while loading the file content
     */
    private static FeatureModel loadUVLFeatureModelWithSpecificPaths(String rootModelPath, Map<String, String> subModelPaths) throws IOException {
        Path filePath = Paths.get(rootModelPath);
        String content = new String(Files.readAllBytes(filePath));
        UVLModelFactory uvlModelFactory = new UVLModelFactory();
        FeatureModel featureModel = uvlModelFactory.parse(content, subModelPaths);
        return featureModel;
    }

    private static void traverseConstraint(Constraint constraint) {
        for (Constraint subConstraint : constraint.getConstraintSubParts()) {
            //... do something with constraint
            traverseConstraint(subConstraint);
        }
    }

    public static void traverseAllFeatures(Feature feature) {
        for (Group group : feature.getChildren()) {
            for (Feature childFeature : group.getFeatures()) {
                //... do something with feature
                //or stop at submodel with if(!childfeature.isSubmodelroot())
                traverseAllFeatures(childFeature);
            }
        }
    }
}
