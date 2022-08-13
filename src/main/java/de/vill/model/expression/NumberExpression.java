package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class NumberExpression extends Expression{
    public double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    private double number;

    public NumberExpression(double number){
        this.number = number;
    }

    public String toString(){
        return toString(true, "");
    }

    public String toString(boolean withSubmodels, String currentAlias){
        if ((number == Math.floor(number)) && !Double.isInfinite(number)) {
            return Integer.toString((int)number);
        }
        return Double.toString(number);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {

    }

}
