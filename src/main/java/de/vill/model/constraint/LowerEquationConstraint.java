package de.vill.model.constraint;

import de.vill.model.expression.Expression;

public class LowerEquationConstraint extends ExpressionConstraint{
    public LowerEquationConstraint(Expression left, Expression right){
        super(left,right,"<");
    }

}
