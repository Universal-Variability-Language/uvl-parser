package de.vill.model;

public class SumAggregateFunctionExpression extends AggregateFunctionExpression{


    public SumAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public SumAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        return super.toString(withSubmodels, "sum", currentAlias);
    }
}
