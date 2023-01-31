package de.vill.model.typelevel;

import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;
import de.vill.model.expression.Expression;
import java.util.Arrays;
import java.util.List;

public class NumericFeatureEqualsConstraint extends NumericFeatureConstraint {
    public NumericFeatureEqualsConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
        super(left, right, "==", isRightConstant);
    }
}
