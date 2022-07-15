package de.vill.model;

public class AvgAggregateFunctionExpression extends AggregateFunctionExpression{


    public AvgAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public AvgAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        return super.toString(withSubmodels, "avg", currentAlias);
    }
}
