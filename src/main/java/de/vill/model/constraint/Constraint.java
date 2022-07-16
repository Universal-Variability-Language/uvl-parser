package de.vill.model.constraint;

public abstract class Constraint {
    @Override
    public String toString(){
        return toString(true, "");
    }

    public abstract String toString(boolean withSubmodels, String currentAlias);



}
