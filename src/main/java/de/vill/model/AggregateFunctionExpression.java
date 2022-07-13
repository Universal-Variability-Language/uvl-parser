package de.vill.model;

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
    public String toString(boolean withSubmodels) {
        return toString(withSubmodels, "aggregateFunction");
    }

    protected String toString(boolean withSubmodels, String functionName) {
        StringBuilder result = new StringBuilder();
        result.append(functionName+"(");
        if(getRootFeature() != null && withSubmodels){
            if(getRootFeature().isImported()){
                result.append(getRootFeature().getNAME());
                result.append(", ");
            }else{
                if(getRootFeature().getNameSpace() != null){
                    result.append(getRootFeature().getNameSpace() + "." + getRootFeature().getNAME());
                    result.append(", ");
                }else {
                    result.append(getRootFeature().getNAME());
                    result.append(", ");
                }
            }
        }
        result.append(getAttributeName());
        result.append(")");
        return result.toString();
    }
}
