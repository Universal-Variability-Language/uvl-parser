package de.vill.model.constraint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class NumericFeatureConstraint extends Constraint {
    private LiteralConstraint left;
    private LiteralConstraint right;
    private final String inequalitySymbol;
    private final Boolean isRightConstant;

    public NumericFeatureConstraint(final LiteralConstraint left, final LiteralConstraint right, final String inequalitySymbol, final Boolean isRightConstant) {
        this.left = left;
        this.right = right;
        this.inequalitySymbol = inequalitySymbol;
        this.isRightConstant = isRightConstant;
    }

    public LiteralConstraint getLeft() {
        return this.left;
    }

    public LiteralConstraint getRight() {
        return this.right;
    }

    public String getInequalitySymbol() {
        return this.inequalitySymbol;
    }

    public Boolean getIsRightConstant() {
        return this.isRightConstant;
    }

    public boolean evaluate() {
        final Double featureVal = Double.parseDouble(this.left.getFeature().getAttributes().get("type_level_value").getValue().toString());
        final Double rightVal;
        if (this.isRightConstant) {
            rightVal = Double.parseDouble(this.right.getLiteral());
        } else {
            rightVal = Double.parseDouble(this.right.getFeature().getAttributes().get("type_level_value").getValue().toString());
        }
        switch (this.inequalitySymbol) {
            case "==":
                return featureVal.equals(rightVal);
            case ">":
                return featureVal > rightVal;
            case ">=":
                return featureVal >= rightVal;
            case "<":
                return featureVal < rightVal;
            case "<=":
                return featureVal <= rightVal;
            case "!=":
                return !featureVal.equals(rightVal);
        }
        return false;
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return "numcmp(" + this.left.toString(withSubmodels, currentAlias) + "," +
            (this.isRightConstant ? this.right.getLiteral() : this.right.toString(withSubmodels, currentAlias)) + "," +
            this.inequalitySymbol + ")";
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        if (this.isRightConstant) {
            return Collections.singletonList(this.left);
        }
        return Arrays.asList(this.left, this.right);
    }

    @Override
    public void replaceConstraintSubPart(final Constraint oldSubConstraint, final Constraint newSubConstraint) {
        if (this.left == oldSubConstraint) {
            this.left = (LiteralConstraint) newSubConstraint;
        } else if (this.right == oldSubConstraint) {
            this.right = (LiteralConstraint) newSubConstraint;
        }
    }

    @Override
    public int hashCode(final int level) {
        final int prime = 31;
        int result = prime * level + (this.left == null ? 0 : this.left.hashCode());
        result = prime * result + (this.right == null ? 0 : this.right.hashCode());
        result = prime * result + (this.inequalitySymbol == null ? 0 : this.inequalitySymbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final NumericFeatureConstraint other = (NumericFeatureConstraint) obj;
        return Objects.equals(this.inequalitySymbol, other.inequalitySymbol) && Objects.equals(this.left, other.left)
            && Objects.equals(this.right, other.right);
    }
}
