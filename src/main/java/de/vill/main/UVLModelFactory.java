package de.vill.main;

import de.vill.UVLLexer;
import de.vill.UVLParser;
import de.vill.exception.ParseError;
import de.vill.model.*;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.LiteralExpression;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class UVLModelFactory {

    /**
     * This method parses the givel text and returns a {@link FeatureModel} if everything is fine or throws a {@link ParseError} if something went wrong.
     * @param text A String that describes a feature model in UVL notation.
     * @param fileLoader A Map, that maps every imported feature model from its namespace to a path, where the acutal model is
     * @return A {@link FeatureModel} based on the uvl text
     * @throws ParseError If there is an error during parsing or the construction of the feature model
     */
    public FeatureModel parse(String text, Map<String, String> fileLoader) throws ParseError {
        Function<String, String> fileloaderFunction = x -> fileLoader.get(x);
        return parse(text, fileloaderFunction);
    }

    /**
     * This method parses the givel text and returns a {@link FeatureModel} if everything is fine or throws a {@link ParseError} if something went wrong.
     * @param text A String that describes a feature model in UVL notation.
     * @param path Path to the directory where all submodels are stored.
     * @return A {@link FeatureModel} based on the uvl text
     * @throws ParseError If there is an error during parsing or the construction of the feature model
     */
    public FeatureModel parse(String text, String path) throws ParseError {

        Function<String, String> fileloaderFunction = x -> path + System.getProperty("file.separator") + x.replace(".", System.getProperty("file.separator")) + ".uvl";
        return parse(text, fileloaderFunction);
    }

    /**
     * This method parses the givel text and returns a {@link FeatureModel} if everything is fine or throws a {@link ParseError} if something went wrong.
     * It assumes that all the necessary submodels are in the current working directory.
     * @param text A String that describes a feature model in UVL notation.
     * @return A {@link FeatureModel} based on the uvl text
     * @throws ParseError If there is an error during parsing or the construction of the feature model
     */
    public FeatureModel parse(String text) throws ParseError {
        Function<String, String> fileloaderFunction = x -> System.getProperty("user.dir") + System.getProperty("file.separator") + x.replace(".", System.getProperty("file.separator")) + ".uvl";
        return parse(text, fileloaderFunction);
    }

    /**
     * This method parses the givel text and returns a {@link FeatureModel} if everything is fine or throws a {@link ParseError} if something went wrong.
     * @param text A String that describes a feature model in UVL notation.
     * @param fileLoader A {@link Function}, that maps every imported feature model from its namespace to a path, where the acutal model is
     * @return A {@link FeatureModel} based on the uvl text
     * @throws ParseError If there is an error during parsing or the construction of the feature model
     */
    public FeatureModel parse(String text, Function<String, String> fileLoader) throws ParseError{
        FeatureModel featureModel = parseFeatureModelWithImports(text,fileLoader, new HashMap<>());
        composeFeatureModelFromImports(featureModel);
        referenceFeaturesInConstraints(featureModel);
        referenceAttributesInConstraints(featureModel);
        referenceRootFeaturesInAggregateFunctions(featureModel);
        return featureModel;
    }

    //TODO If the level set is not consistent e.g. remove SMT_LEVEL but the feature model has AGGREGATE_FUNCTION level? -> remove automatically all related constraints (auch in der BA schrieben (In conversion strats chaper diskutieren))
    /**
     * This method takes a {@link FeatureModel} and transforms it so that it does not use any of the specified {@link LanguageLevel}.
     * It does that by removing the concepts of the level without any conversion strategies. This means information
     * is lost and the configuration space of the feature model will most likely change. It can be used, if the actual
     * conversion strategies are not performant enough.
     * @param featureModel A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param levelsToDrop All levels that should be removed from the feature model.
     */
    public void dropLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> levelsToDrop){}

    private LanguageLevel getMaxLanguageLevel(Set<LanguageLevel> languageLevels){
        LanguageLevel max = LanguageLevel.SAT_LEVEL;
        for(LanguageLevel languageLevel : languageLevels){
            if(languageLevel.getValue() > max.getValue()){
                max = languageLevel;
            }
        }
        return max;
    }

    /**
     * First Language Level in the list (index 0) should be removed first and so on. calculates based on a set levels
     * that should be remove which levels actually needs to be removed. E.g. if a major level should be removed, all
     * its corresponding minor levels must be removed too. Moreover the levels must be removed in the correct order,
     * so the "highest" level must be removed first. Furthermore levels that are not used by the featuremodel must
     * not be removed.
     * @param featureModel The featuremodel that does used language levels
     * @param levelsToRemove The levels that a user thinks should be removed.
     * @return a list with the language levels that acutally need to be removed in the order of the list (first element of the list removed first)
     */
    private List<LanguageLevel> getActualLanguageLevelsToRemoveInOrder(FeatureModel featureModel, Set<LanguageLevel> levelsToRemove){
        List<LanguageLevel> completeOrderedLevelsToRemove = new LinkedList<>();
        while (!levelsToRemove.isEmpty()) {
            LanguageLevel highestLevel = getMaxLanguageLevel(levelsToRemove);
            if (LanguageLevel.isMajorLevel(highestLevel)) {
                //highestLevel is major level
                int numberCorrespondingMinorLevels = highestLevel.getValue()+1;
                List<LanguageLevel> correspondingMinorLevels = LanguageLevel.valueOf(numberCorrespondingMinorLevels);
                if(correspondingMinorLevels != null){
                    correspondingMinorLevels.retainAll(featureModel.getUsedLanguageLevels());
                    completeOrderedLevelsToRemove.addAll(correspondingMinorLevels);
                }
                completeOrderedLevelsToRemove.add(highestLevel);
                levelsToRemove.remove(highestLevel);
            } else {
                //highestLevel is minor level
                completeOrderedLevelsToRemove.add(highestLevel);
                levelsToRemove.remove(highestLevel);
            }
        }

        return completeOrderedLevelsToRemove;
    }

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it does not use any of the specified {@link UVLModelFactory#dropLanguageLevel(FeatureModel, Set)}.
     * It does that applying different conversion strategies, trying to keep as much information as possible. This means
     * that the conversion can take a long time and my not be feasible for large models. If so try to just drop the levels instead.
     * @param featureModel A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param levelsToDrop All levels that should be removed from the feature model.
     */
    public void convertLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> levelsToDrop){}

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it only uses the specified {@link LanguageLevel}.
     * It just inverts the Set and calls {@link UVLModelFactory#dropLanguageLevel(FeatureModel, Set)}.
     * @param featureModel A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param levelsToDrop All levels that can stay in the feature model.
     */
    public void dropExceptAcceptedLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> levelsToDrop, Set<LanguageLevel> supportedLanguageLevel){
        Set<LanguageLevel> allLevels = new HashSet<>(Arrays.asList(LanguageLevel.values()));
        allLevels.removeAll(supportedLanguageLevel);

        dropLanguageLevel(featureModel, allLevels);
    }

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it only uses the specified {@link LanguageLevel}.
     * It just inverts the Set and calls {@link UVLModelFactory#convertLanguageLevel(FeatureModel, Set)}.
     * @param featureModel A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param levelsToDrop All levels that can stay in the feature model.
     */
    public void convertExceptAcceptedLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> levelsToDrop, Set<LanguageLevel> supportedLanguageLevel){
        Set<LanguageLevel> allLevels = new HashSet<>(Arrays.asList(LanguageLevel.values()));
        allLevels.removeAll(supportedLanguageLevel);

        convertLanguageLevel(featureModel, allLevels);
    }

    private FeatureModel parseFeatureModelWithImports(String text, Function<String, String> fileLoader, Map<String, Import> visitedImports){
        UVLLexer uvlLexer = new UVLLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(uvlLexer);
        UVLParser uvlParser = new UVLParser(tokens);

        uvlParser.addErrorListener(new BaseErrorListener(){
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new ParseError(line, charPositionInLine,"failed to parse at line " + line + ":" + charPositionInLine + " due to " + msg, e);
            }
        });

        UVLListener uvlListener = new UVLListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(uvlListener, uvlParser.featureModel());

        FeatureModel featureModel = uvlListener.getFeatureModel();
        featureModel.getOwnConstraints().addAll(featureModel.getConstraints());


        visitedImports.put(featureModel.getNamespace(), null);

        for(Import importLine : featureModel.getImports()){
            if(visitedImports.containsKey(importLine.getNamespace()) && visitedImports.get(importLine.getNamespace()) == null){
                throw new ParseError("Cyclic import detected! " + "The import of " + importLine.getNamespace() + " in " + featureModel.getNamespace() + " creates a cycle");
            }else {
                try {
                    String path = fileLoader.apply(importLine.getNamespace());
                    Path filePath = Path.of(path);
                    String content = Files.readString(filePath);
                    FeatureModel subModel = parseFeatureModelWithImports(content, fileLoader, visitedImports);
                    importLine.setFeatureModel(subModel);
                    subModel.getRootFeature().setRelatedImport(importLine);
                    visitedImports.put(importLine.getNamespace(), importLine);
                    featureModel.getConstraints().addAll(subModel.getConstraints());

                    for (Map.Entry<String, Feature> entry : subModel.getFeatureMap().entrySet()) {
                        Feature feature = entry.getValue();
                        if(feature.getNameSpace().equals("")){
                            feature.setNameSpace(importLine.getAlias());
                        }else {
                            feature.setNameSpace(importLine.getAlias() + "." + feature.getNameSpace());
                        }
                        if(!featureModel.getFeatureMap().containsKey(feature.getNameSpace() + "." + entry.getValue().getFeatureName())) {
                            featureModel.getFeatureMap().put(feature.getNameSpace() + "." + entry.getValue().getFeatureName(), feature);
                        }
                    }
                } catch (IOException e) {
                    throw new ParseError("Could not resolve import: " + e.getMessage());
                }
            }
        }

        return featureModel;
    }

    private void composeFeatureModelFromImports(FeatureModel featureModel){
        for (Map.Entry<String, Feature> entry : featureModel.getFeatureMap().entrySet()) {
            if(entry.getValue().isSubmodelRoot()){
                Feature featureInMainFeatureTree = entry.getValue();
                Import relatedImport = featureInMainFeatureTree.getRelatedImport();
                Feature featureInSubmodelFeatureTree = relatedImport.getFeatureModel().getRootFeature();
                featureInMainFeatureTree.getChildren().addAll(featureInSubmodelFeatureTree.getChildren());
                featureInMainFeatureTree.getAttributes().putAll(featureInSubmodelFeatureTree.getAttributes());
                relatedImport.getFeatureModel().setRootFeature(featureInMainFeatureTree);
                //TODO das alte Root Feature vom Submodell (newFeature) ist noch in den feature maps?

            }
        }
    }

    private List<FeatureModel> createSubModelList(FeatureModel featureModel){
        var subModelList = new LinkedList<FeatureModel>();
        for(Import importLine : featureModel.getImports()){
            subModelList.add(importLine.getFeatureModel());
            subModelList.addAll(createSubModelList(importLine.getFeatureModel()));
        }
        return subModelList;
    }

    private void referenceFeaturesInConstraints(FeatureModel featureModel){
        var subModelList = createSubModelList(featureModel);
        var literalConstraints = featureModel.getLiteralConstraints();
        for(LiteralConstraint constraint : literalConstraints){
            Feature referencedFeature = featureModel.getFeatureMap().get(constraint.getLiteral());
            if(referencedFeature == null){
                throw new ParseError("Feature " + constraint + " is referenced in a constraint in" + featureModel.getNamespace() + " but does not exist as feature in the tree!");
            }else {
                constraint.setFeature(referencedFeature);
            }
        }
        for(FeatureModel subModel : subModelList){
            literalConstraints = subModel.getLiteralConstraints();
            for(LiteralConstraint constraint : literalConstraints){
                Feature referencedFeature = subModel.getFeatureMap().get(constraint.getLiteral());
                if(referencedFeature == null){
                    throw new ParseError("Feature " + constraint + " is referenced in a constraint in" + subModel.getNamespace() + " but does not exist as feature in the tree!");
                }else {
                    constraint.setFeature(referencedFeature);
                }
            }
        }
    }

    private void referenceAttributesInConstraints(FeatureModel featureModel){
        var subModelList = createSubModelList(featureModel);
        var literalExpressions = featureModel.getLiteralExpressions();
        for(LiteralExpression expression : literalExpressions){
            String reference = expression.getAttributeName();
            String[] referenceParts = reference.split("\\.");
            String attributeName = referenceParts[referenceParts.length-1];
            String featureName = reference.substring(0, reference.length()-attributeName.length()-1);
            expression.setAttributeName(attributeName);
            Feature referencedFeature = featureModel.getFeatureMap().get(featureName);
            if(referencedFeature == null || referencedFeature.getAttributes().get(attributeName) == null){
                throw new ParseError("Attribute " + featureName + "." + attributeName + " is referenced in a constraint in " + featureModel.getNamespace() + " but does not exist as feature in the tree!");
            }else {
                expression.setFeature(referencedFeature);
            }
        }
        for(FeatureModel subModel : subModelList){
            literalExpressions = subModel.getLiteralExpressions();
            for(LiteralExpression expression : literalExpressions){
                String reference = expression.getAttributeName();
                String[] referenceParts = reference.split("\\.");
                String attributeName = referenceParts[referenceParts.length-1];
                String featureName = reference.substring(0, reference.length()-attributeName.length()-1);
                expression.setAttributeName(attributeName);
                Feature referencedFeature = subModel.getFeatureMap().get(featureName);
                if(referencedFeature == null || referencedFeature.getAttributes().get(attributeName) == null){
                    throw new ParseError("Attribute " + featureName + "." + attributeName + " is referenced in a constraint in " + subModel.getNamespace() + " but does not exist as feature in the tree!");
                }else {
                    expression.setFeature(referencedFeature);
                }
            }
        }
    }

    private void referenceRootFeaturesInAggregateFunctions(FeatureModel featureModel){
        var subModelList = createSubModelList(featureModel);
        var aggregateFunctionExpressions = featureModel.getAggregateFunctionsWithRootFeature();
        for(AggregateFunctionExpression expression : aggregateFunctionExpressions){
            Feature referencedFeature = featureModel.getFeatureMap().get(expression.getRootFeatureName());
            if(referencedFeature == null){
                throw new ParseError("Feature is used in aggregate function " + expression.toString() + " but does not exist as feature in the tree!");
            }else {
                expression.setRootFeature(referencedFeature);
            }
        }
        for(FeatureModel subModel : subModelList){
            aggregateFunctionExpressions = subModel.getAggregateFunctionsWithRootFeature();
            for(AggregateFunctionExpression expression : aggregateFunctionExpressions){
                Feature referencedFeature = subModel.getFeatureMap().get(expression.getRootFeatureName());
                if(referencedFeature == null){
                    throw new ParseError("Feature is used in aggregate function " + expression.toString() + " but does not exist as feature in the tree!");
                }else {
                    expression.setRootFeature(referencedFeature);
                }
            }
        }
    }
}
