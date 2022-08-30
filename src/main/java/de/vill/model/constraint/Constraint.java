package de.vill.model.constraint;

import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;

import java.util.List;

public abstract class Constraint {
    @Override
    public String toString(){
        return toString(true, "");
    }

    public abstract String toString(boolean withSubmodels, String currentAlias);

    public abstract List<Constraint> getConstraintSubParts();

    public abstract void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint);


}
