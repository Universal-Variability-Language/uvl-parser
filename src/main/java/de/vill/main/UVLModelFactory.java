package de.vill.main;

import de.vill.UVLLexer;
import de.vill.UVLParser;
import de.vill.conversion.ConvertAggregateFunction;
import de.vill.conversion.ConvertFeatureCardinality;
import de.vill.conversion.ConvertGroupCardinality;
import de.vill.conversion.ConvertNumericConstraints;
import de.vill.conversion.ConvertSMTLevel;
import de.vill.conversion.ConvertStringConstraints;
import de.vill.conversion.ConvertTypeLevel;
import de.vill.conversion.DropAggregateFunction;
import de.vill.conversion.DropFeatureCardinality;
import de.vill.conversion.DropGroupCardinality;
import de.vill.conversion.DropNumericConstraints;
import de.vill.conversion.DropStringConstraints;
import de.vill.conversion.DropSMTLevel;
import de.vill.conversion.DropTypeLevel;
import de.vill.conversion.IConversionStrategy;
import de.vill.exception.ParseError;
import de.vill.exception.ParseErrorList;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.Import;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;
import de.vill.util.Constants;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class UVLModelFactory {

    private final Map<LanguageLevel, Class<? extends IConversionStrategy>> conversionStrategiesDrop;
    private final Map<LanguageLevel, Class<? extends IConversionStrategy>> conversionStrategiesConvert;

    private final List<ParseError> errorList = new LinkedList<>();

    public UVLModelFactory() {
        this.conversionStrategiesDrop = new HashMap<>();
        this.conversionStrategiesDrop.put(LanguageLevel.GROUP_CARDINALITY, DropGroupCardinality.class);
        this.conversionStrategiesDrop.put(LanguageLevel.FEATURE_CARDINALITY, DropFeatureCardinality.class);
        this.conversionStrategiesDrop.put(LanguageLevel.AGGREGATE_FUNCTION, DropAggregateFunction.class);
        this.conversionStrategiesDrop.put(LanguageLevel.ARITHMETIC_LEVEL, DropSMTLevel.class);
        this.conversionStrategiesDrop.put(LanguageLevel.TYPE_LEVEL, DropTypeLevel.class);
        this.conversionStrategiesDrop.put(LanguageLevel.STRING_CONSTRAINTS, DropStringConstraints.class);
        this.conversionStrategiesDrop.put(LanguageLevel.NUMERIC_CONSTRAINTS, DropNumericConstraints.class);
        this.conversionStrategiesConvert = new HashMap<>();
        this.conversionStrategiesConvert.put(LanguageLevel.GROUP_CARDINALITY, ConvertGroupCardinality.class);
        this.conversionStrategiesConvert.put(LanguageLevel.FEATURE_CARDINALITY, ConvertFeatureCardinality.class);
        this.conversionStrategiesConvert.put(LanguageLevel.AGGREGATE_FUNCTION, ConvertAggregateFunction.class);
        this.conversionStrategiesConvert.put(LanguageLevel.ARITHMETIC_LEVEL, ConvertSMTLevel.class);
        this.conversionStrategiesConvert.put(LanguageLevel.TYPE_LEVEL, ConvertTypeLevel.class);
        this.conversionStrategiesConvert.put(LanguageLevel.STRING_CONSTRAINTS, ConvertStringConstraints.class);
        this.conversionStrategiesConvert.put(LanguageLevel.NUMERIC_CONSTRAINTS, ConvertNumericConstraints.class);
    }

    /**
     * This method parses the givel text and returns a {@link FeatureModel} if everything is fine or throws a {@link ParseError} if something went wrong.
     *
     * @param text       A String that describes a feature model in UVL notation.
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
     *
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
     *
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
     *
     * @param text       A String that describes a feature model in UVL notation.
     * @param fileLoader A {@link Function}, that maps every imported feature model from its namespace to a path, where the acutal model is
     * @return A {@link FeatureModel} based on the uvl text
     * @throws ParseError If there is an error during parsing or the construction of the feature model
     */
    public FeatureModel parse(String text, Function<String, String> fileLoader) throws ParseError {
        FeatureModel featureModel = parseFeatureModelWithImports(text, fileLoader, new HashMap<>());
        composeFeatureModelFromImports(featureModel);
        referenceFeaturesInConstraints(featureModel);
        referenceAttributesInConstraints(featureModel);
        referenceRootFeaturesInAggregateFunctions(featureModel);
        validateTypeLevelConstraints(featureModel);
        return featureModel;
    }

    public Constraint parseConstraint(String constraintString) throws ParseError {
        constraintString = constraintString.trim();
        UVLLexer uvlLexer = new UVLLexer(CharStreams.fromString(constraintString));
        CommonTokenStream tokens = new CommonTokenStream(uvlLexer);
        UVLParser uvlParser = new UVLParser(tokens);
        uvlParser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        uvlLexer.removeErrorListener(ConsoleErrorListener.INSTANCE);

        uvlLexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                errorList.add(new ParseError(line, charPositionInLine, "failed to parse at line " + line + ":" + charPositionInLine + " due to: " + msg, e));
            }
        });
        uvlParser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                errorList.add(new ParseError(line, charPositionInLine, "failed to parse at line " + line + ":" + charPositionInLine + " due to " + msg, e));
            }
        });

        UVLListener uvlListener = new UVLListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(uvlListener, uvlParser.constraintLine());
        Constraint constraint = null;

        return uvlListener.getConstraint();
    }

    //TODO If the level set is not consistent e.g. remove SMT_LEVEL but the feature model has AGGREGATE_FUNCTION level? -> remove automatically all related constraints (auch in der BA schrieben (In conversion strats chaper diskutieren))

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it does not use any of the specified
     * {@link LanguageLevel}. This method applies the conversion strategy on the featuremodel and on all
     * submodels that import the corresponding language level.
     * It does that by removing the concepts of the level without any conversion strategies. This means information
     * is lost and the configuration space of the feature model will most likely change. It can be used, if the actual
     * conversion strategies are not performant enough.
     *
     * @param featureModel A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param levelsToDrop All levels that should be removed from the feature model.
     */
    public void dropLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> levelsToDrop) {
        convertFeatureModel(featureModel, featureModel, levelsToDrop, conversionStrategiesDrop);
    }

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it does not use any of the specified
     * {@link UVLModelFactory#dropLanguageLevel(FeatureModel, Set)}. This method applies the conversion strategy on the
     * featuremodel and on all submodels that import the corresponding language level.
     * It does that applying different conversion strategies, trying to keep as much information as possible. This means
     * that the conversion can take a long time and my not be feasible for large models. If so try to just drop the levels instead.
     *
     * @param featureModel    A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param levelsToConvert All levels that should be removed from the feature model.
     */
    public void convertLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> levelsToConvert) {
        convertFeatureModel(featureModel, featureModel, levelsToConvert, conversionStrategiesConvert);
    }

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it only uses the specified {@link LanguageLevel}.
     * It just inverts the Set and calls {@link UVLModelFactory#dropLanguageLevel(FeatureModel, Set)}.
     *
     * @param featureModel           A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param supportedLanguageLevel All levels that can stay in the feature model.
     */
    public void dropExceptAcceptedLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> supportedLanguageLevel) {
        Set<LanguageLevel> allLevels = new HashSet<>(Arrays.asList(LanguageLevel.values()));
        allLevels.removeAll(supportedLanguageLevel);

        dropLanguageLevel(featureModel, allLevels);
    }

    /**
     * This method takes a {@link FeatureModel} and transforms it so that it only uses the specified {@link LanguageLevel}.
     * It just inverts the Set and calls {@link UVLModelFactory#convertLanguageLevel(FeatureModel, Set)}.
     *
     * @param featureModel           A reference to the feature model which should be transformed. The method operates directly on this object, not on a clone!
     * @param supportedLanguageLevel All levels that can stay in the feature model.
     */
    public void convertExceptAcceptedLanguageLevel(FeatureModel featureModel, Set<LanguageLevel> supportedLanguageLevel) {
        Set<LanguageLevel> allLevels = new HashSet<>(Arrays.asList(LanguageLevel.values()));
        allLevels.removeAll(supportedLanguageLevel);

        convertLanguageLevel(featureModel, allLevels);
    }

    /**
     * This method takes a {@link FeatureModel} converts all language levels higher than the specified {@link LanguageLevel}.
     * For instance, providing the SMT major level will remove all SMT minor and type levels.
     * @param featureModel Reference to feature model to be converted
     * @param supportedLanguageLevel Highest language level to be maintained
     */
    public void convertAllMoreComplexLanguageLevels(FeatureModel featureModel, LanguageLevel supportedLanguageLevel) {
        Set<LanguageLevel> levelsToConvert = new HashSet<>();

        for (LanguageLevel level : LanguageLevel.values()) {
            if (level.getValue() > supportedLanguageLevel.getValue()) {
                levelsToConvert.add(level);
            }
        }

        convertLanguageLevel(featureModel, levelsToConvert);

    }

    private void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel, Set<LanguageLevel> levelsToRemove, Map<LanguageLevel, Class<? extends IConversionStrategy>> conversionStrategies) {
        List<LanguageLevel> levelsToRemoveActually = getActualLanguageLevelsToRemoveInOrder(featureModel, levelsToRemove);
        while (!levelsToRemoveActually.isEmpty()) {
            LanguageLevel levelToDropNow = levelsToRemoveActually.get(0);
            levelsToRemoveActually.remove(0);
            try {
                IConversionStrategy conversionStrategy = conversionStrategies.get(levelToDropNow).getDeclaredConstructor().newInstance();
                conversionStrategy.convertFeatureModel(rootFeatureModel, featureModel);
                featureModel.getUsedLanguageLevels().removeAll(conversionStrategy.getLevelsToBeRemoved());
                featureModel.getUsedLanguageLevels().addAll(conversionStrategy.getTargetLevelsOfConversion());
                for (Import importLine : featureModel.getImports()) {
                    //only consider sub feature models from imports that are actually used
                    if (importLine.isReferenced()) {
                        convertFeatureModel(rootFeatureModel, importLine.getFeatureModel(), levelsToRemove, conversionStrategies);
                        featureModel.getUsedLanguageLevels().removeAll(conversionStrategy.getLevelsToBeRemoved());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private LanguageLevel getMaxLanguageLevel(Set<LanguageLevel> languageLevels) {
        LanguageLevel max = LanguageLevel.BOOLEAN_LEVEL;
        for (LanguageLevel languageLevel : languageLevels) {
            if (languageLevel.getValue() > max.getValue()) {
                max = languageLevel;
            }
        }
        return max;
    }

    /**
     * First Language Level in the list (index 0) should be removed first and so on. calculates based on a set levels
     * that should be removed which levels actually needs to be removed. E.g. if a major level should be removed, all
     * its corresponding minor levels must be removed too. Moreover, the levels must be removed in the correct order,
     * so the "highest" level must be removed first. Furthermore, levels that are not used by the featureModel must
     * not be removed.
     *
     * @param featureModel   The featureModel that does use language levels
     * @param levelsToRemove The levels that a user thinks should be removed.
     * @return a list with the language levels that actually need to be removed in the order of the list (first element of the list removed first)
     */
    private List<LanguageLevel> getActualLanguageLevelsToRemoveInOrder(FeatureModel featureModel, Set<LanguageLevel> levelsToRemove) {
        Set<LanguageLevel> levelsToRemoveClone = new HashSet<>(levelsToRemove);
        List<LanguageLevel> completeOrderedLevelsToRemove = new LinkedList<>();
        while (!levelsToRemoveClone.isEmpty()) {
            LanguageLevel highestLevel = getMaxLanguageLevel(levelsToRemoveClone);
            if (LanguageLevel.isMajorLevel(highestLevel)) {
                //highestLevel is major level
                int numberCorrespondingMinorLevels = highestLevel.getValue() + 1;
                List<LanguageLevel> correspondingMinorLevels = LanguageLevel.valueOf(numberCorrespondingMinorLevels);
                if (correspondingMinorLevels != null) {
                    correspondingMinorLevels.retainAll(featureModel.getUsedLanguageLevelsRecursively());
                    completeOrderedLevelsToRemove.addAll(correspondingMinorLevels);
                }
                completeOrderedLevelsToRemove.add(highestLevel);
                levelsToRemoveClone.remove(highestLevel);
            } else {
                //highestLevel is minor level
                if (featureModel.getUsedLanguageLevelsRecursively().contains(highestLevel)) {
                    completeOrderedLevelsToRemove.add(highestLevel);
                }
                levelsToRemoveClone.remove(highestLevel);
            }
        }
        //SAT-level can not be removed
        completeOrderedLevelsToRemove.remove(LanguageLevel.BOOLEAN_LEVEL);

        return completeOrderedLevelsToRemove;
    }

    private FeatureModel parseFeatureModelWithImports(String text, Function<String, String> fileLoader, Map<String, Import> visitedImports) {
        //remove leading and trailing spaces (to be more robust)
        text = text.trim();
        UVLLexer uvlLexer = new UVLLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(uvlLexer);
        UVLParser uvlParser = new UVLParser(tokens);
        uvlParser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        uvlLexer.removeErrorListener(ConsoleErrorListener.INSTANCE);

        uvlLexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                errorList.add(new ParseError(line, charPositionInLine, "failed to parse at line " + line + ":" + charPositionInLine + " due to: " + msg, e));
                //throw new ParseError(line, charPositionInLine,"failed to parse at line " + line + ":" + charPositionInLine + " due to: " + msg, e);
            }
        });
        uvlParser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                errorList.add(new ParseError(line, charPositionInLine, "failed to parse at line " + line + ":" + charPositionInLine + " due to " + msg, e));
                //throw new ParseError(line, charPositionInLine,"failed to parse at line " + line + ":" + charPositionInLine + " due to " + msg, e);
            }
        });


        UVLListener uvlListener = new UVLListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(uvlListener, uvlParser.featureModel());
        FeatureModel featureModel = null;

        try {
            featureModel = uvlListener.getFeatureModel();
        } catch (ParseErrorList e) {
            errorList.addAll(e.getErrorList());
        }

        if (errorList.size() > 0) {
            ParseErrorList parseErrorList = new ParseErrorList("Multiple Errors occurred during parsing!");
            parseErrorList.getErrorList().addAll(errorList);
            throw parseErrorList;
        }

        //if featuremodel has not namespace and no root feature getNamespace returns null
        if(featureModel.getNamespace() != null) {
            visitedImports.put(featureModel.getNamespace(), null);

            for (Import importLine : featureModel.getImports()) {
                if (visitedImports.containsKey(importLine.getNamespace()) && visitedImports.get(importLine.getNamespace()) == null) {
                    throw new ParseError("Cyclic import detected! " + "The import of " + importLine.getNamespace() + " in " + featureModel.getNamespace() + " creates a cycle", importLine.getLineNumber());
                } else {
                    try {
                        String path = fileLoader.apply(importLine.getNamespace());
                        Path filePath = Paths.get(path);
                        String content = new String(Files.readAllBytes(filePath));
                        FeatureModel subModel = parseFeatureModelWithImports(content, fileLoader, visitedImports);
                        importLine.setFeatureModel(subModel);
                        subModel.getRootFeature().setRelatedImport(importLine);
                        visitedImports.put(importLine.getNamespace(), importLine);

                        //adjust namespaces of imported features
                        for (Map.Entry<String, Feature> entry : subModel.getFeatureMap().entrySet()) {
                            Feature feature = entry.getValue();
                            if (feature.getNameSpace().equals("")) {
                                feature.setNameSpace(importLine.getAlias());
                            } else {
                                feature.setNameSpace(importLine.getAlias() + "." + feature.getNameSpace());
                            }
                        }

                        //check if submodel is acutally used
                        if (featureModel.getFeatureMap().containsKey(subModel.getRootFeature().getReferenceFromSpecificSubmodel(""))) {
                            importLine.setReferenced(true);
                        }
                        // if submodel is used add features
                        if (importLine.isReferenced()) {
                            for (Map.Entry<String, Feature> entry : subModel.getFeatureMap().entrySet()) {
                                Feature feature = entry.getValue();
                                if (!featureModel.getFeatureMap().containsKey(feature.getNameSpace() + "." + entry.getValue().getFeatureName())) {
                                    if (importLine.isReferenced()) {
                                        featureModel.getFeatureMap().put(feature.getNameSpace() + "." + entry.getValue().getFeatureName(), feature);
                                    }
                                }
                            }
                        }


                    } catch (IOException e) {
                        throw new ParseError("Could not resolve import: " + e.getMessage(), importLine.getLineNumber());
                    }
                }
            }
        }

        return featureModel;
    }

    private void composeFeatureModelFromImports(FeatureModel featureModel) {
        for (Map.Entry<String, Feature> entry : featureModel.getFeatureMap().entrySet()) {
            if (entry.getValue().isSubmodelRoot()) {
                Feature featureInMainFeatureTree = entry.getValue();
                Import relatedImport = featureInMainFeatureTree.getRelatedImport();
                Feature featureInSubmodelFeatureTree = relatedImport.getFeatureModel().getRootFeature();
                featureInMainFeatureTree.getChildren().addAll(featureInSubmodelFeatureTree.getChildren());
                for (Group group : featureInMainFeatureTree.getChildren()) {
                    group.setParentFeature(featureInMainFeatureTree);
                }
                featureInMainFeatureTree.getAttributes().putAll(featureInSubmodelFeatureTree.getAttributes());
                relatedImport.getFeatureModel().setRootFeature(featureInMainFeatureTree);
            }
        }
    }

    private List<FeatureModel> createSubModelList(FeatureModel featureModel) {
        List<FeatureModel> subModelList = new LinkedList<FeatureModel>();
        for (Import importLine : featureModel.getImports()) {
            subModelList.add(importLine.getFeatureModel());
            subModelList.addAll(createSubModelList(importLine.getFeatureModel()));
        }
        return subModelList;
    }

    private void referenceFeaturesInConstraints(FeatureModel featureModel) {
        List<FeatureModel> subModelList = createSubModelList(featureModel);
        List<LiteralConstraint> literalConstraints = featureModel.getLiteralConstraints();
        for (LiteralConstraint constraint : literalConstraints) {
            Feature referencedFeature = featureModel.getFeatureMap().get(constraint.getLiteral().replace("\'", ""));
            if (referencedFeature == null) {
                throw new ParseError("Feature " + constraint + " is referenced in a constraint in " + featureModel.getNamespace() + " but does not exist as feature in the tree!", constraint.getLineNumber());
            } else {
                constraint.setFeature(referencedFeature);
            }
        }
        for (FeatureModel subModel : subModelList) {
            literalConstraints = subModel.getLiteralConstraints();
            for (LiteralConstraint constraint : literalConstraints) {
                Feature referencedFeature = subModel.getFeatureMap().get(constraint.getLiteral().replace("\'", ""));
                if (referencedFeature == null) {
                    throw new ParseError("Feature " + constraint + " is referenced in a constraint in " + subModel.getNamespace() + " but does not exist as feature in the tree!", constraint.getLineNumber());
                } else {
                    constraint.setFeature(referencedFeature);
                }
            }
        }
    }

    private void referenceAttributesInConstraints(final FeatureModel featureModel) {
        final List<FeatureModel> subModelList = createSubModelList(featureModel);
        List<LiteralExpression> literalExpressions = featureModel.getLiteralExpressions();
        for (final LiteralExpression expression : literalExpressions) {
            final Feature referencedFeature = featureModel.getFeatureMap().get(expression.getFeatureName());
            if (referencedFeature == null || (expression.getAttributeName() != null && referencedFeature.getAttributes().get(expression.getAttributeName()) == null)) {
                throw new ParseError("Attribute " + expression.getFeatureName() + "." + expression.getAttributeName() + " is referenced in a constraint in " + featureModel.getNamespace() + " but does not exist as feature in the tree!", expression.getLineNumber());
            } else {
                expression.setFeature(referencedFeature);
            }
        }
        for (final FeatureModel subModel : subModelList) {
            literalExpressions = subModel.getLiteralExpressions();
            for (final LiteralExpression expression : literalExpressions) {
                final Feature referencedFeature = subModel.getFeatureMap().get(expression.getFeatureName());
                if (referencedFeature == null || referencedFeature.getAttributes().get(expression.getAttributeName()) == null) {
                    throw new ParseError("Attribute " + expression.getFeatureName() + "." + expression.getAttributeName() + " is referenced in a constraint in " + subModel.getNamespace() + " but does not exist as feature in the tree!", expression.getLineNumber());
                } else {
                    expression.setFeature(referencedFeature);
                }
            }
        }
    }

    private void referenceRootFeaturesInAggregateFunctions(FeatureModel featureModel) {
        final List<FeatureModel> subModelList = createSubModelList(featureModel);
        List<AggregateFunctionExpression> aggregateFunctionExpressions = featureModel.getAggregateFunctionsWithRootFeature();
        for (final AggregateFunctionExpression expression : aggregateFunctionExpressions) {
            final Feature referencedFeature = featureModel.getFeatureMap().get(expression.getRootFeatureName().replace("\"", ""));
            if (referencedFeature == null) {
                throw new ParseError("Feature " + expression.getRootFeatureName() + " is used in aggregate function " + expression.toString() + " but does not exist as feature in the tree!", expression.getLineNumber());
            } else {
                expression.setRootFeature(referencedFeature);
            }
        }
        for (FeatureModel subModel : subModelList) {
            aggregateFunctionExpressions = subModel.getAggregateFunctionsWithRootFeature();
            for (final AggregateFunctionExpression expression : aggregateFunctionExpressions) {
                final Feature referencedFeature = subModel.getFeatureMap().get(expression.getRootFeatureName().replace("\"", ""));
                if (referencedFeature == null) {
                    throw new ParseError("Feature " + expression.getRootFeatureName() + " is used in aggregate function " + expression.toString() + " but does not exist as feature in the tree!", expression.getLineNumber());
                } else {
                    expression.setRootFeature(referencedFeature);
                }
            }
        }
    }

    private void validateTypeLevelConstraints(final FeatureModel featureModel) {
        final List<Constraint> constraints = featureModel.getOwnConstraints();
        for (final Constraint constraint: constraints) {
            if (!validateTypeLevelConstraint(constraint)) {
                throw new ParseError("Invalid Constraint in line - " + constraint.getLineNumber());
            }
        }
    }

    private boolean validateTypeLevelConstraint(final Constraint constraint) {
        boolean result = true;
        if (constraint instanceof ExpressionConstraint) {
            String leftReturnType = ((ExpressionConstraint) constraint).getLeft().getReturnType();
            String rightReturnType = ((ExpressionConstraint) constraint).getRight().getReturnType();

            if (!(leftReturnType.equalsIgnoreCase(Constants.TRUE) || rightReturnType.equalsIgnoreCase(Constants.TRUE))) {
                // if not attribute constraint
                result = result && ((ExpressionConstraint) constraint).getLeft().getReturnType().equalsIgnoreCase(((ExpressionConstraint) constraint).getRight().getReturnType());
            }
            if (!result) {
                return false;
            }
            for (final Expression expr: ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                result = result && validateTypeLevelExpression(expr);
            }
        }

        for (final Constraint subCons: constraint.getConstraintSubParts()) {
            result = result && validateTypeLevelConstraint(subCons);
        }

        return result;
    }

    private boolean validateTypeLevelExpression(final Expression expression) {
        final String initial = expression.getReturnType();
        boolean result = true;

        for (final Expression expr: expression.getExpressionSubParts()) {
            result = result && validateTypeLevelExpression(expr) && initial.equalsIgnoreCase(expr.getReturnType());
        }

        return result;
    }
}
