package de.vill.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

import de.vill.exception.ParseError;
import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;

public class DemoMain {

    UVLModelFactory factory;

    private Set<LanguageLevel> satLevel;
    private Set<LanguageLevel> smtLevel;
    private Set<LanguageLevel> typeLevel;

    public DemoMain() {
        factory = new UVLModelFactory();
        initializeLevelSets();
    }

    public static void main(String[] args) throws ParseError, IOException {
        DemoMain demo = new DemoMain();

        // Original SAT Level
        demo.convertAllMoreComplex("demo/SAT/groupcard.uvl", LanguageLevel.BOOLEAN_LEVEL);

        // // Original SMT Level
        // demo.convertAllMoreComplex("demo/SMT/smtbasic.uvl", LanguageLevel.BOOLEAN_LEVEL);
        // demo.convertAllMoreComplex("demo/SMT/aggregate.uvl", LanguageLevel.ARITHMETIC_LEVEL);
        // demo.convertAllMoreComplex("demo/SMT/featcard.uvl", LanguageLevel.BOOLEAN_LEVEL);

        // // Original TYPE level
        // demo.convertAllMoreComplex("demo/TYPE/typebasic.uvl", LanguageLevel.ARITHMETIC_LEVEL);
        // demo.convertAllMoreComplex("demo/TYPE/stringconstraints.uvl", LanguageLevel.TYPE_LEVEL);
    }

    private FeatureModel loadUvlModel(String path) throws ParseError, IOException {
        return factory.parse(Files.readString(Paths.get(path)));
    }

    private void convertModel(String path, Set<LanguageLevel> supportedLevels) {
        try {
            FeatureModel originalFeatureModel = loadUvlModel(path);
            factory.convertExceptAcceptedLanguageLevel(originalFeatureModel, supportedLevels);
            Files.write(updatePath(path, supportedLevels), originalFeatureModel.toString().getBytes());
        } catch (ParseError | IOException i) {
            i.printStackTrace();
        }

    }

    private void convertAllMoreComplex(String path, LanguageLevel maxSupportedLevel) {
        try {
            FeatureModel originalFeatureModel = loadUvlModel(path);
            factory.convertAllMoreComplexLanguageLevels(originalFeatureModel, maxSupportedLevel);
            Files.write(updatePath(path, maxSupportedLevel), originalFeatureModel.toString().getBytes());
        } catch (ParseError | IOException i) {
            i.printStackTrace();
        }
    }

    private Path updatePath(String originalPath, Set<LanguageLevel> supportedLevels) {
        String fileName = originalPath.split("\\.")[0] + "-";
        for (LanguageLevel level : supportedLevels) {
            fileName += level.getName();
        }
        return Paths.get(fileName + ".uvl");
    }

    private Path updatePath(String originalPath, LanguageLevel supportedLevel) {
        String fileName = originalPath.split("\\.")[0] + "-";
        fileName += supportedLevel.getName();
        return Paths.get(fileName + ".uvl");
    }

    private void initializeLevelSets() {
        satLevel = new LinkedHashSet<>();
        satLevel.add(LanguageLevel.BOOLEAN_LEVEL);
        smtLevel = new LinkedHashSet<>();
        smtLevel.add(LanguageLevel.ARITHMETIC_LEVEL);
        typeLevel = new LinkedHashSet<>();
        typeLevel.add(LanguageLevel.TYPE_LEVEL);
    }
}
