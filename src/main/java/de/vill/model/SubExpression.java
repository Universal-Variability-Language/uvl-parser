package de.vill.model;

public class SubExpression extends Expression{
    private Expression left;
    private Expression right;

    public SubExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(left.toString());
        result.append(" - ");
        result.append(right.toString());
        return result.toString();
    }
}
