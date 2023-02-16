package de.vill.model.typelevel;

import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;

public class NumericFeatureGreaterConstraint extends NumericFeatureConstraint {
    private final LiteralConstraint left;
    private final LiteralConstraint right;
    private final Boolean isRightConstant;

    public NumericFeatureGreaterConstraint(final LiteralConstraint left, final LiteralConstraint right, final Boolean isRightConstant) {
        super(left, right, ">", isRightConstant);
        this.left = left;
        this.right = right;
        this.isRightConstant = isRightConstant;
    }

    @Override
    public Constraint clone() {
        return new NumericFeatureGreaterConstraint(this.left, this.right, this.isRightConstant);
    }
}
