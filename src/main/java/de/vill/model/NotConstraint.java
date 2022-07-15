package de.vill.model;

public class NotConstraint extends Constraint{
    private Constraint content;

    public NotConstraint(Constraint content){
        this.content = content;
    }

    public Constraint getContent() {
        return content;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        result.append("!");
        result.append(content.toString(withSubmodels, currentAlias));
        return result.toString();
    }
}
