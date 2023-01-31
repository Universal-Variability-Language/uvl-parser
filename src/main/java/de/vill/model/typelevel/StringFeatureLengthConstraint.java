package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;
import de.vill.model.constraint.StringFeatureConstraint;

public class StringFeatureLengthConstraint extends StringFeatureConstraint {
    public StringFeatureLengthConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
        super(left, right, "length", isRightConstant);
    }
}
