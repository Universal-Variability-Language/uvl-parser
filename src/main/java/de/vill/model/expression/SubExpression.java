package de.vill.model.expression;

public class SubExpression extends Expression{
    private Expression left;
    private Expression right;

    public SubExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        result.append(left.toString(withSubmodels, currentAlias));
        result.append(" - ");
        result.append(right.toString(withSubmodels, currentAlias));
        return result.toString();
    }
}
