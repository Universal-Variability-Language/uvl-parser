package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class MinAggregateFunctionExpression extends AggregateFunctionExpression {

    public MinAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public MinAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return super.toString(withSubmodels, "min", currentAlias);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }
}
