package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.StringFeatureConstraint;

public class StringFeatureAssignmentConstraint extends StringFeatureConstraint {
    private final LiteralConstraint left;
    private final LiteralConstraint right;
    private final Boolean isRightConstant;

    public StringFeatureAssignmentConstraint(final LiteralConstraint left, final LiteralConstraint right, final Boolean isRightConstant) {
        super(left, right, "equals", isRightConstant);
        this.left = left;
        this.right = right;
        this.isRightConstant = isRightConstant;
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return "strval(" + this.left.toString(withSubmodels, currentAlias) + "," +
            (this.isRightConstant ? this.right.getLiteral() : this.right.toString(withSubmodels, currentAlias)) + ")";
    }
}
