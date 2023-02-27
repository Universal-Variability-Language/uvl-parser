package de.vill.model.constraint;

import de.vill.model.expression.Expression;
import java.util.Collections;
import java.util.List;

public class LowerEquationConstraint extends ExpressionConstraint {
    private Expression left;
    private Expression right;

    public LowerEquationConstraint(Expression left, Expression right) {
        super(left, right, "<");
        this.left = left;
        this.right = right;
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Collections.emptyList();
    }

    @Override
    public Constraint clone() {
        return new LowerEquationConstraint(this.left, this.right);
    }
}
