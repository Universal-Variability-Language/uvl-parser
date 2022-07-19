package de.vill.model.constraint;

import de.vill.model.expression.Expression;

public class EqualEquationConstraint extends ExpressionConstraint{

    public EqualEquationConstraint(Expression left, Expression right){
        super(left,right,"==");
    }

}
