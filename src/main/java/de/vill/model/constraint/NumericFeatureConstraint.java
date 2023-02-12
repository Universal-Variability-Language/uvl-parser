package de.vill.model.constraint;

import de.vill.model.typelevel.NumericFeatureEqualsConstraint;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class NumericFeatureConstraint extends Constraint {
    private LiteralConstraint left;
    private LiteralConstraint right;
    private String inequalitySymbol;
    private Boolean isRightConstant;

    public NumericFeatureConstraint(LiteralConstraint left, LiteralConstraint right, String inequalitySymbol, Boolean isRightConstant) {
        this.left = left;
        this.right = right;
        this.inequalitySymbol = inequalitySymbol;
        this.isRightConstant = isRightConstant;
    }

    public LiteralConstraint getLeft() {
        return left;
    }

    public LiteralConstraint getRight() {
        return right;
    }

    public String getInequalitySymbol() {
        return inequalitySymbol;
    }

    public Boolean getIsRightConstant() {
        return isRightConstant;
    }

    public boolean evaluate() {
        Double featureVal = Double.parseDouble(left.getFeature().getAttributes().get("type_level_default_value").getValue().toString());
        Double rightVal;
        if (isRightConstant) {
            rightVal = Double.parseDouble(right.getLiteral());
        } else {
            rightVal = Double.parseDouble(right.getFeature().getAttributes().get("type_level_default_value").getValue().toString());
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
    public String toString(boolean withSubmodels, String currentAlias) {
        return "numcmp(" + left.toString(withSubmodels, currentAlias) + "," +
            (isRightConstant ? right.getLiteral() : right.toString(withSubmodels, currentAlias)) + "," +
            inequalitySymbol + ")";
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        if (isRightConstant) {
            return Collections.singletonList(left);
        }
        return Arrays.asList(left, right);
    }

    @Override
    public void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint) {
        if (left == oldSubConstraint) {
            left = (LiteralConstraint) newSubConstraint;
        } else if (right == oldSubConstraint) {
            right = (LiteralConstraint) newSubConstraint;
        }
    }

    @Override
    public Constraint clone() {
        // TODO implement clone method in expressions and clone them here
        return new NumericFeatureEqualsConstraint(left, right, isRightConstant);
    }

    @Override
    public int hashCode(int level) {
        final int prime = 31;
        int result = prime * level + (left == null ? 0 : left.hashCode());
        result = prime * result + (right == null ? 0 : right.hashCode());
        result = prime * result + (inequalitySymbol == null ? 0 : inequalitySymbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NumericFeatureConstraint other = (NumericFeatureConstraint) obj;
        return Objects.equals(inequalitySymbol, other.inequalitySymbol) && Objects.equals(left, other.left)
            && Objects.equals(right, other.right);
    }
}
