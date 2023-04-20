package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class MaxAggregateFunctionExpression extends AggregateFunctionExpression {
    public MaxAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public MaxAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return super.toString(withSubmodels, "max", currentAlias);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }
}
