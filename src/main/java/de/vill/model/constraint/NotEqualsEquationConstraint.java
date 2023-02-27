package de.vill.model.constraint;

import de.vill.model.expression.Expression;
import java.util.Collections;
import java.util.List;

public class NotEqualsEquationConstraint extends ExpressionConstraint {
    private Expression left;
    private Expression right;

    public NotEqualsEquationConstraint(Expression left, Expression right) {
        super(left, right, "!=");
        this.left = left;
        this.right = right;
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Collections.emptyList();
    }

    @Override
    public Constraint clone() {
        return new NotEqualsEquationConstraint(this.left, this.right);
    }
}
