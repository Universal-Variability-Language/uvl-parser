package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class LengthAggregateFunctionExpression extends AggregateFunctionExpression {
    public LengthAggregateFunctionExpression(final String featureName) {
        super(featureName, null);
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return super.toString(withSubmodels, "len", currentAlias);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }
}
