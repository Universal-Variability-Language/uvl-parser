package de.vill.model;

public class GreaterEquationConstraint extends Constraint{
    private Expression left;
    private Expression right;

    public GreaterEquationConstraint(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString(boolean withSubmodels){
        StringBuilder result = new StringBuilder();
        result.append(left.toString());
        result.append(" > ");
        result.append(right.toString());
        return result.toString();
    }
}
