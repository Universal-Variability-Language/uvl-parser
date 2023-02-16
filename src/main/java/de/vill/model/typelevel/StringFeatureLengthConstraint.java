package de.vill.model.typelevel;

import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.StringFeatureConstraint;

public class StringFeatureLengthConstraint extends StringFeatureConstraint {
    private final LiteralConstraint left;
    private final LiteralConstraint right;
    private final Boolean isRightConstant;

    public StringFeatureLengthConstraint(LiteralConstraint left, LiteralConstraint right, Boolean isRightConstant) {
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
        String featureVal = left.getFeature().getAttributes().get("type_level_value").getValue().toString();
        String rightVal =
            isRightConstant ? right.getLiteral() : right.getFeature().getAttributes().get("type_level_value").getValue().toString();

        return featureVal.length() == rightVal.length();
    }
}
