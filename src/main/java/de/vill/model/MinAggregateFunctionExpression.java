package de.vill.model;

public class MinAggregateFunctionExpression extends AggregateFunctionExpression {


    public MinAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public MinAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        return super.toString(withSubmodels, "min", currentAlias);
    }
}
