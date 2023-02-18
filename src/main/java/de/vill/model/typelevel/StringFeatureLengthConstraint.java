package de.vill.model.typelevel;

import de.vill.model.FeatureType;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.StringFeatureConstraint;

public class StringFeatureLengthConstraint extends StringFeatureConstraint {
    private final LiteralConstraint left;
    private final LiteralConstraint right;
    private final Boolean isRightConstant;

    public StringFeatureLengthConstraint(final LiteralConstraint left, final LiteralConstraint right, final Boolean isRightConstant) {
        super(left, right, "length", isRightConstant);
        this.left = left;
        this.right = right;
        this.isRightConstant = isRightConstant;
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return "strlen(" + this.left.toString(withSubmodels, currentAlias) + "," +
            (this.isRightConstant ? this.right.getLiteral() : this.right.toString(withSubmodels, currentAlias)) + ")";
    }

    public boolean evaluate() {
        final String featureVal = this.left.getFeature().getAttributes().get("type_level_value").getValue().toString();
        final String rightVal =
            this.isRightConstant ? this.right.getLiteral() : this.right.getFeature().getAttributes().get("type_level_value").getValue().toString();
        if (!isRightConstant && FeatureType.INT.equals(this.right.getFeature().getFeatureType())) {
            return featureVal.length() == Integer.parseInt(rightVal);
        }
        return featureVal.length() == rightVal.length();
    }

    @Override
    public Constraint clone() {
        return new StringFeatureEqualsConstraint(this.left, this.right, this.isRightConstant);
    }
}
