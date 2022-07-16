package de.vill.model.expression;

import de.vill.model.Feature;

public class AggregateFunctionExpression extends Expression{
    public String getRootFeatureName() {
        return rootFeatureName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    private String rootFeatureName;

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    private String attributeName;

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }

    private Feature rootFeature;

    public AggregateFunctionExpression(String attributeName){
        this.attributeName = attributeName;
    }
    public AggregateFunctionExpression(String rootFeatureName, String attributeName){
        this(attributeName);
        this.rootFeatureName = rootFeatureName;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return toString(withSubmodels, "aggregateFunction", currentAlias);
    }

    protected String toString(boolean withSubmodels, String functionName, String currentAlias) {
        StringBuilder result = new StringBuilder();
        result.append(functionName+"(");

        if(getRootFeature() != null){
            if(withSubmodels){
                result.append(getRootFeature().getFullReference());
            }else {
                result.append(getRootFeatureName());
            }
            result.append(", ");
        }

        result.append(getAttributeName());
        result.append(")");
        return result.toString();
    }
}
