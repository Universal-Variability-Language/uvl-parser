package de.vill.model.expression;

import de.vill.util.Constants;
import java.util.Collections;
import java.util.List;

public class AvgAggregateFunctionExpression extends AggregateFunctionExpression {
    public AvgAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public AvgAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return super.toString(withSubmodels, "avg", currentAlias);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Collections.emptyList();
    }

    @Override
    public String getReturnType() {
        return Constants.NUMBER;
    }
}
