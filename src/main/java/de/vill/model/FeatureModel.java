package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.*;

public class FeatureModel {
    private Feature rootFeature;
    private String namespace;

    public Set<LanguageLevel> getUsedLanguageLevels() {
        return usedLanguageLevels;
    }

    private Set<LanguageLevel> usedLanguageLevels = new HashSet<>(){{add(LanguageLevel.SAT_LEVEL);}};

    private List<Import> imports = new LinkedList<>();

    public List<LiteralConstraint> getLiteralConstraints() {
        return literalConstraints;
    }

    private List<LiteralConstraint> literalConstraints = new LinkedList<>();

    public List<LiteralExpression> getLiteralExpressions() {
        return literalExpressions;
    }

    private List<LiteralExpression> literalExpressions = new LinkedList<>();

    public List<AggregateFunctionExpression> getAggregateFunctionsWithRootFeature() {
        return aggregateFunctionsWithRootFeature;
    }

    private List<AggregateFunctionExpression> aggregateFunctionsWithRootFeature = new LinkedList<>();

    private List<Constraint> constraints = new LinkedList<>();

    private List<Constraint> ownConstraints = new LinkedList<>();
    private Map<String, Feature> featureMap = new HashMap<>();

    public List<Constraint> getConstraints() {
        return constraints;
    }
    public List<Constraint> getOwnConstraints() {
        return ownConstraints;
    }

    public Map<String, Feature> getFeatureMap() {
        return featureMap;
    }

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }
    public String getNamespace() {
        if (namespace == null){
            return rootFeature.getFeatureName();
        }else {
            return namespace;
        }
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    public List<Import> getImports() {
        return imports;
    }

    /**
     * Prints the current featuremodel without composing it with the other models.
     * @return the uvl representation of the current model without submodels
     */
    @Override
    public String toString(){
        return toString("");
    }

    public String toString(String currentAlias){
        StringBuilder result = new StringBuilder();
        if(namespace != null) {
            result.append("namespace ");
            result.append(namespace);
            result.append(Configuration.NEWLINE);
            result.append(Configuration.NEWLINE);
        }
        if(imports.size() > 0){
            result.append("imports");
            result.append(Configuration.NEWLINE);
            for(Import importLine : imports){
                result.append(Configuration.TABULATOR);
                result.append(importLine.getNamespace());
                if(importLine.getAlias() != null){
                    result.append(" as ");
                    result.append(importLine.getAlias());
                }
                result.append(Configuration.NEWLINE);
            }
            result.append(Configuration.NEWLINE);
        }
        if(rootFeature != null) {
            result.append("features");
            result.append(Configuration.NEWLINE);
            result.append(Util.indentEachLine(getRootFeature().toStringAsRoot(currentAlias)));
            result.append(Configuration.NEWLINE);
        }
        if(getOwnConstraints().size() > 0) {
            result.append("constraints");
            result.append(Configuration.NEWLINE);
            for(Constraint constraint : ownConstraints){
                result.append(Configuration.TABULATOR);
                result.append(constraint.toString(false, currentAlias));
                result.append(Configuration.NEWLINE);
            }
        }
        return result.toString();
    }

    /**
     * WARNING: This method prints the composed model in a uvl LIKE syntax. The output is not valid UVL.
     * This is because imported features are named with <namespace>.<featurename> but dots are not allowed
     * in uvl names
     * @return uvl LIKE string representing the UVL model
     */
    public String composedModelToString(){
        StringBuilder result = new StringBuilder();
        if(namespace != null) {
            result.append("namespace ");
            result.append(namespace);
            result.append(Configuration.NEWLINE);
            result.append(Configuration.NEWLINE);
        }
        if(rootFeature != null) {
            result.append("features");
            result.append(Configuration.NEWLINE);
            result.append(Util.indentEachLine(getRootFeature().toString()));
            result.append(Configuration.NEWLINE);
        }
        if(getConstraints().size() > 0) {
            result.append("constraints");
            result.append(Configuration.NEWLINE);
            for(Constraint constraint : constraints){
                result.append(Configuration.TABULATOR);
                result.append(constraint.toString(true, ""));
                result.append(Configuration.NEWLINE);
            }
        }
        return result.toString();
    }

    /**
     * Prints the feature model and all it submodels to uvl according to its original decomposition.
     * @return a map with namespaces of featuremodel and submodels as key and uvl strings as value
     */
    public Map<String, String> decomposedModelToString(){
        return decomposedModelToString("");
    }

    private Map<String, String> decomposedModelToString(String currentAlias){
        var models = new HashMap<String, String>();
        models.put(getNamespace(), toString(currentAlias));

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
}
