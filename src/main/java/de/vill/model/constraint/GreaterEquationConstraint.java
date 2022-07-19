package de.vill.model.constraint;

import de.vill.model.expression.Expression;

public class GreaterEquationConstraint extends ExpressionConstraint{
    public GreaterEquationConstraint(Expression left, Expression right){
        super(left, right, ">");
    }
}
