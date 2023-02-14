package de.vill.model.constraint;

import de.vill.model.Feature;
import de.vill.model.expression.Expression;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AssignmentEquationConstraint extends ExpressionConstraint {

    public AssignmentEquationConstraint(Expression left, Expression right) {
        super(left, right, "=");
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Arrays.asList();
    }

    @Override
    //TODO
    public boolean evaluate(Set<Feature> selectedFeatures) {
        return true;
    }

}
