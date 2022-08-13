package de.vill.model.constraint;

import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;

import java.util.Arrays;
import java.util.List;

public abstract class ExpressionConstraint extends Constraint{
    private Expression left;
    private Expression right;
    private String expressionSymbol;

    public ExpressionConstraint(Expression left, Expression right, String expressionSymbol){
        this.left = left;
        this.right = right;
        this.expressionSymbol = expressionSymbol;
    }

    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        result.append(left.toString(withSubmodels, currentAlias));
        result.append(" ");
        result.append(expressionSymbol);
        result.append(" ");
        result.append(right.toString(withSubmodels, currentAlias));
        return result.toString();
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Arrays.asList();
    }

    public List<Expression> getExpressionSubParts(){
        return Arrays.asList(left, right);
    }

    public void replaceExpressionSubPart(AggregateFunctionExpression oldSubExpression, Expression newSubExpression) {
        if(left == oldSubExpression){
            left = newSubExpression;
        } else if (right == oldSubExpression) {
            right = newSubExpression;
        }
    }
}
