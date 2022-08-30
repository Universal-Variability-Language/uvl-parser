package de.vill.model.constraint;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Arrays.asList(content);
    }

    @Override
    public void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint) {
        if(content == oldSubConstraint){
            content = newSubConstraint;
        }
    }
}
