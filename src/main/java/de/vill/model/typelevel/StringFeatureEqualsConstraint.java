package de.vill.model.typelevel;

import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.StringFeatureConstraint;

public class StringFeatureEqualsConstraint extends StringFeatureConstraint {
    private final LiteralConstraint left;
    private final LiteralConstraint right;
    private final Boolean isRightConstant;

    public StringFeatureEqualsConstraint(final LiteralConstraint left, final LiteralConstraint right, final Boolean isRightConstant) {
        super(left, right, "equals", isRightConstant);
        this.left = left;
        this.right = right;
        this.isRightConstant = isRightConstant;
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return "strcmp(" + this.left.toString(withSubmodels, currentAlias) + "," +
            (this.isRightConstant ? this.right.getLiteral() : this.right.toString(withSubmodels, currentAlias)) + ")";
    }

    public boolean evaluate() {
        final String featureVal = this.left.getFeature().getAttributes().get("type_level_value").getValue().toString();
        final String rightVal =
            this.isRightConstant ? this.right.getLiteral() : this.right.getFeature().getAttributes().get("type_level_value").getValue().toString();

        return featureVal.equalsIgnoreCase(rightVal);
    }

    @Override
    public Constraint clone() {
        return new StringFeatureEqualsConstraint(this.left, this.right, this.isRightConstant);
    }
}
