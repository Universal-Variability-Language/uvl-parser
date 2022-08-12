package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class NumberExpression extends Expression{
    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
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
        return Double.toString(number);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

}
