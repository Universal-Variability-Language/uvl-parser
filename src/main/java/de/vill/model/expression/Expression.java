package de.vill.model.expression;

import java.util.List;

public abstract class Expression {
    public String toString(){
        return toString(true, "");
    }

    public abstract String toString(boolean withSubmodels, String currentAlias);

    public abstract List<Expression> getExpressionSubParts();

    public abstract void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression);
}
