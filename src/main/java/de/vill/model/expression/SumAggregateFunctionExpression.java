package de.vill.model.expression;

import de.vill.util.Constants;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SumAggregateFunctionExpression extends AggregateFunctionExpression {
    public SumAggregateFunctionExpression(String attributeName) {
        super(attributeName);
    }

    public SumAggregateFunctionExpression(String rootFeatureName, String attributeName) {
        super(rootFeatureName, attributeName);
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return super.toString(withSubmodels, "sum", currentAlias);
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
