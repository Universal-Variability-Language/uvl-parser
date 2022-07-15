package de.vill.model;

public class MaxAggregateFunctionExpression extends AggregateFunctionExpression{


    public MaxAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public MaxAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        return super.toString(withSubmodels, "max", currentAlias);
    }
}
