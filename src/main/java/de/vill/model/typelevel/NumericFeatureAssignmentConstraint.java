package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;

public class NumericFeatureAssignmentConstraint extends NumericFeatureConstraint {
    private final LiteralConstraint left;
    private final LiteralConstraint right;
    private final Boolean isRightConstant;

    public NumericFeatureAssignmentConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
        super(left, right, "=", isRightConstant);
        this.left = left;
        this.right = right;
        this.isRightConstant = isRightConstant;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return "numval(" + left.toString(withSubmodels, currentAlias) + "," +
            (isRightConstant ? right.getLiteral() : right.toString(withSubmodels, currentAlias)) + ")";
    }
}
