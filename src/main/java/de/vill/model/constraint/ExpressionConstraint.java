package de.vill.model.constraint;

import de.vill.model.Feature;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    @Override
    public void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint) {
        //no sub constraints
    }

    public boolean evaluate(Set<Feature> selectedFeatures){
        double leftResult;
        if(left instanceof LiteralExpression && !selectedFeatures.contains(((LiteralExpression) left).getFeature())){
            leftResult = 0;
        }else {
            leftResult = left.evaluate(selectedFeatures);
        }
        double rightResult;
        if(right instanceof LiteralExpression && !selectedFeatures.contains(((LiteralExpression) right).getFeature())){
            rightResult = 0;
        }else {
            rightResult = right.evaluate(selectedFeatures);
        }

        if(expressionSymbol.equals("==")){
            return leftResult == rightResult;
        }else if(expressionSymbol.equals("<")){
            return leftResult < rightResult;
        }else if(expressionSymbol.equals(">")){
            return leftResult > rightResult;
        }
        return false;
    }

    @Override
    public Constraint clone() {
        //TODO implement clone method in expressions and clone them here
        return new EqualEquationConstraint(left, right);
    }
}
