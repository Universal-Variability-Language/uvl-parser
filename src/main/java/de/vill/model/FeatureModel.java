package de.vill.model;

import de.vill.config.Configuration;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.LiteralExpression;
import de.vill.util.Util;

import java.util.*;

import static de.vill.util.Util.addNecessaryQuotes;

/**
 * This class represents a feature model and all its sub featuremodels if the model is composed.
 */
public class FeatureModel {
    private final Set<LanguageLevel> usedLanguageLevels = new HashSet<>(){{add(LanguageLevel.SAT_LEVEL);}};
    private String namespace;
    private final List<Import> imports = new LinkedList<>();
    private Feature rootFeature;
    private final Map<String, Feature> featureMap = new HashMap<>();
    private final List<Constraint> ownConstraints = new LinkedList<>();

    /*
     * These three lists are just for performance. They contain all points where features are referenced
     * e.g. in constraints or aggregatefunctions. The corresponding feature can not be set during parsing,
     * because the feature may be in a submodel which is not parsed then. Therefore we strore all this objects to
     * reference the features after everything is parsed without searching for them.
     */
    private final List<LiteralConstraint> literalConstraints = new LinkedList<>();
    private final List<LiteralExpression> literalExpressions = new LinkedList<>();
    private final List<AggregateFunctionExpression> aggregateFunctionsWithRootFeature = new LinkedList<>();

    /**
     * Be very careful when creating your own featuremodel to set all information in all objects.
     * Especially when working with decomposed models it is important to set all namespaces etc. right. If you are not
     * sure look in the {@link de.vill.main.UVLModelFactory} class how the feature model is assembled there.
     */
    public FeatureModel(){}

    /**
     * Get a set with all in this featuremodel used language levels (major and minor).
     * This list contains the levels used in this feature model and all its sub feature models
     * The returned set is a copy, therefore changing it will NOT change the featuremodel.
     * @return the used language levels as set
     */
    public Set<LanguageLevel> getUsedLanguageLevelsRecursively() {
        Set<LanguageLevel> languageLevels = new HashSet<>();
        languageLevels.addAll(getUsedLanguageLevels());
        for(Import importLine : imports){
            languageLevels.addAll(importLine.getFeatureModel().getUsedLanguageLevelsRecursively());
        }
        return languageLevels;
    }

    /**
     * Get a set with all in this featuremodel used language levels (major and minor).
     * This list contains the levels used in this feature model but not the ones used in submodels.
     * The returned set is no copy, therefore changing it will change the featuremodel.
     * @return the used language levels as set
     */
    public Set<LanguageLevel> getUsedLanguageLevels() {
        return usedLanguageLevels;
    }

    /**
     * Returns the namespace of the featuremodel. If no namespace is set, the featuremodel returns the name of
     * the root feature.
     * @return the namespace of the feature model
     */
    public String getNamespace() {
        if (namespace == null){
            return rootFeature.getFeatureName();
        }else {
            return namespace;
        }
    }

    /**
     * Setter for the namespace of the featuremodel.
     * @param namespace Namespace of the featuremodel.
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * This method returns a list of the imports of the feature model. The list does not contain recursivly all
     * imports (including imports of imported feature models), just the directly imported ones in this feature model.
     * This list is no clone. Changing it will actually change the feature model.
     * @return A list containing all imports of this feature model.
     */
    public List<Import> getImports() {
        return imports;
    }

    /**
     * Get the root feature of the feature model
     * @return root feature
     */
    public Feature getRootFeature() {
        return rootFeature;
    }

    /**
     * Set the root feature of the feature model
     * @param rootFeature the root feature
     */
    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }

    /**
     * This map contains all features of this featuremodel and recursivly all features of its imported submodels.
     * This means in a decomposed feature model only the feature model object of the root feature model contains all
     * features over all sub feature models in this map. The key is the reference with namespace and feature name viewed
     * from this model (this means the key is how the feature is referenced from this feature model). Therefore
     * in the root feature model the key for each feature is its complete reference from the root feature model.
     * When adding or deleting features from this map the featuremodel gets inconsistent when not also adding / removing
     * them from the feature tree.
     * @return A map with all features of the feature model
     */
    public Map<String, Feature> getFeatureMap() {
        return featureMap;
    }

    /**
     * A list with all the constraints of this featuremodel. This does not contain the constraints of the imported
     * sub feature models.
     * @return A list of the constraints of this featuremodel.
     */
    public List<Constraint> getOwnConstraints() {
        return ownConstraints;
    }

    /**
     * A list will all constraints of this featuremodel and recursively of all its imported sub feature models. This list
     * is not stored but gets calculated with every call. This means changing a constraint will have an effect to the feature model,
     * but adding deleting constraints will have no effect. This must be done in the correspoding ownConstraint lists of the feature models.
     * This means when calling this method on the root feature model it returns with all constraints of the decomposed
     * feature model.
     * @return a list will all constraints of this feature model.
     */
    public List<Constraint> getConstraints() {
        var constraints = new LinkedList<Constraint>();
        constraints.addAll(ownConstraints);
        for(Import importLine : imports){
            constraints.addAll(importLine.getFeatureModel().getConstraints());
        }
        return constraints;
    }

    /**
     * Prints the current featuremodel without composing it with the other models.
     * @return the uvl representation of the current model without submodels
     */
    @Override
    public String toString(){
        return toString(false, "");
    }

    /**
     * Returns a single uvl feature model composed out of all submodels. To acoid naming conflicts all feature
     * names are changed and a unique id is added. If you do not work with decomposed models do not this method for
     * printing, use the other print methods instead!
     * @return A single uvl representation of the composed model.
     */
    public String composedModelToString(){
        return this.toString(true,"");
    }

    /**
     * Prints the feature model and all it submodels to uvl according to its original decomposition. This method
     * should be called on the root feature model.
     * @return a map with namespaces of featuremodel and submodels as key and uvl strings as value
     */
    public Map<String, String> decomposedModelToString(){
        return decomposedModelToString("");
    }

    private Map<String, String> decomposedModelToString(String currentAlias){
        var models = new HashMap<String, String>();
        models.put(getNamespace(), toString(false, currentAlias));
        for(Import importLine : imports){
            String newCurrentAlias;
            if(currentAlias.equals("")){
                newCurrentAlias = importLine.getAlias();
            }else {
                newCurrentAlias = currentAlias + "." + importLine.getAlias();
            }
            models.putAll(importLine.getFeatureModel().decomposedModelToString(newCurrentAlias));
        }
        return models;
    }

    private String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        if(usedLanguageLevels.size() != 0){
            result.append("include");
            result.append(Configuration.getNewlineSymbol());
            Set<LanguageLevel> levelsToPrint;
            if(withSubmodels){
                levelsToPrint = getUsedLanguageLevelsRecursively();
            }else {
                levelsToPrint = getUsedLanguageLevels();
            }
            for(LanguageLevel languageLevel : getUsedLanguageLevels()){
                result.append(Configuration.getTabulatorSymbol());
                if(LanguageLevel.isMajorLevel(languageLevel)){
                    result.append(languageLevel.getName());
                }else {
                    result.append(LanguageLevel.valueOf(languageLevel.getValue()-1).get(0).getName());
                    result.append(".");
                    result.append(languageLevel.getName());
                }
                result.append(Configuration.getNewlineSymbol());
            }
            result.append(Configuration.getNewlineSymbol());
        }
        if(namespace != null) {
            result.append("namespace ");
            result.append(addNecessaryQuotes(namespace));
            result.append(Configuration.getNewlineSymbol());
            result.append(Configuration.getNewlineSymbol());
        }
        if(imports.size() > 0 && !withSubmodels){
            result.append("imports");
            result.append(Configuration.getNewlineSymbol());
            for(Import importLine : imports){
                result.append(Configuration.getTabulatorSymbol());
                result.append(addNecessaryQuotes(importLine.getNamespace()));
                if(!importLine.getAlias().equals(importLine.getNamespace())){
                    result.append(" as ");
                    result.append(addNecessaryQuotes(importLine.getAlias()));
                }
                result.append(Configuration.getNewlineSymbol());
            }
            result.append(Configuration.getNewlineSymbol());
        }
        if(rootFeature != null) {
            result.append("features");
            result.append(Configuration.getNewlineSymbol());
            if(withSubmodels){
                result.append(Util.indentEachLine(getRootFeature().toString(withSubmodels, currentAlias)));
            }else {
                result.append(Util.indentEachLine(getRootFeature().toStringAsRoot(currentAlias)));
            }
            result.append(Configuration.getNewlineSymbol());
        }
        if(getOwnConstraints().size() > 0) {
            result.append("constraints");
            result.append(Configuration.getNewlineSymbol());
            List<Constraint> constraintList;
            if(withSubmodels){
                constraintList = getConstraints();
            }else{
                constraintList = getOwnConstraints();
            }
            for(Constraint constraint : constraintList){
                result.append(Configuration.getTabulatorSymbol());
                result.append(constraint.toString(withSubmodels, currentAlias));
                result.append(Configuration.getNewlineSymbol());
            }
        }
        return result.toString();
    }

    /**
     * This list exists just for performance reasons when building a decomposed feature model from several uvl files.
     * This list does not have to be set, when building your own feature model. It is also not necessary to update
     * this list when adding for example constraints.
     * @return a list with all {@link LiteralConstraint} objects in the constraints of this feature model.
     */
    public List<LiteralConstraint> getLiteralConstraints() {
        return literalConstraints;
    }

    /**
     * This list exists just for performance reasons when building a decomposed feature model from several uvl files.
     * This list does not have to be set, when building your own feature model. It is also not necessary to update
     * this list when adding for example constraints.
     * @return a list with all {@link LiteralExpression} objects in the constraints of this feature model.
     */
    public List<LiteralExpression> getLiteralExpressions() {
        return literalExpressions;
    }

    /**
     * This list exists just for performance reasons when building a decomposed feature model from several uvl files.
     * This list does not have to be set, when building your own feature model. It is also not necessary to update
     * this list when adding for example constraints.
     * @return a list with all {@link AggregateFunctionExpression} objects in the constraints of this feature model.
     */
    public List<AggregateFunctionExpression> getAggregateFunctionsWithRootFeature() {
        return aggregateFunctionsWithRootFeature;
    }
}
