package de.vill.model;

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
        return toString(true);
    }

    public String toString(boolean withSubmodels){
        return Integer.toString(number);
    }

}
