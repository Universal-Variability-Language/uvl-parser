package de.vill.model;

public class SumAggregateFunctionExpression extends Expression{
    public String getRootFeature() {
        return rootFeature;
    }

    public String getAttributeName() {
        return attributeName;
    }

    private String rootFeature;
    private String attributeName;

    public SumAggregateFunctionExpression(String attributeName){
        this.attributeName = attributeName;
    }
    public SumAggregateFunctionExpression(String rootFeature, String attributeName){
        this(attributeName);
        this.rootFeature = rootFeature;
    }

    @Override
    public String toString(boolean withSubmodels){
        StringBuilder result = new StringBuilder();
        result.append("sum(");
        if(getRootFeature() != null){
            result.append(getRootFeature());
            result.append(", ");
        }
        result.append(getAttributeName());
        result.append(")");
        return result.toString();
    }
}
