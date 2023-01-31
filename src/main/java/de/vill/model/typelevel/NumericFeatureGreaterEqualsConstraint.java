package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;

public class NumericFeatureGreaterEqualsConstraint extends NumericFeatureConstraint {
    public NumericFeatureGreaterEqualsConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
        super(left, right, ">=", isRightConstant);
    }
}
