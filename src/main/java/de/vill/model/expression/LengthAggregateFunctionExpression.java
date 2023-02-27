package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class LengthAggregateFunctionExpression extends AggregateFunctionExpression {
    public LengthAggregateFunctionExpression(String featureName) {
        super(featureName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return super.toString(withSubmodels, "len", currentAlias);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }
}
