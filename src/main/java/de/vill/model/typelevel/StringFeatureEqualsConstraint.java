package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.StringFeatureConstraint;

public class StringFeatureEqualsConstraint extends StringFeatureConstraint {
    public StringFeatureEqualsConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
        super(left, right, "equals", isRightConstant);
    }
}
