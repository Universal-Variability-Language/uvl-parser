package de.vill.model;

public class LiteralConstraint extends Constraint{
    private String literal;

    public LiteralConstraint(String literal){
        this.literal = literal;
    }

    public String getLiteral(){
        return literal;
    }

    @Override
    public String toString(){
        return getLiteral();
    }
}
