package de.vill.model;

public abstract class Expression {
    public String toString(){
        return toString(true, "");
    }

    public abstract String toString(boolean withSubmodels, String currentAlias);
}
