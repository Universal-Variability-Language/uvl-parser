package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;

public class NumericFeatureLowerEqualsConstraint extends NumericFeatureConstraint {
    public NumericFeatureLowerEqualsConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
        super(left, right, "<=", isRightConstant);
    }
}
