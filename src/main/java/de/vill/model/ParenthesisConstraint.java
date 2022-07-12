package de.vill.model;

public class ParenthesisConstraint extends Constraint{
    private Constraint content;

    public ParenthesisConstraint(Constraint content){
        this.content = content;
    }

    public Constraint getContent() {
        return content;
    }

    @Override
    public String toString(boolean withSubmodels){
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(content.toString(withSubmodels));
        result.append(")");
        return result.toString();
    }
}
