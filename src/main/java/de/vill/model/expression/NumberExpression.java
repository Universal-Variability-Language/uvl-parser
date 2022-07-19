package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class NumberExpression extends Expression{
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;

    public NumberExpression(int number){
        this.number = number;
    }

    public String toString(){
        return toString(true, "");
    }

    public String toString(boolean withSubmodels, String currentAlias){
        return Integer.toString(number);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

}
