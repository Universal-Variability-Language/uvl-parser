package de.vill.model.expression;

import java.util.Arrays;
import java.util.List;

public class NumberExpression<T extends Number> extends Expression{
    public T getNumber() {
        return number;
    }

    public void setNumber(T number) {
        this.number = number;
    }

    private T number;

    public NumberExpression(T number){
        this.number = number;
    }

    public String toString(){
        return toString(true, "");
    }

    public String toString(boolean withSubmodels, String currentAlias){
        return number.toString();
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {

    }

}
