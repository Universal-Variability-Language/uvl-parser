package de.vill.model.constraint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class StringFeatureConstraint extends Constraint {
    private LiteralConstraint left;
    private LiteralConstraint right;
    private final String functionName;
    private final Boolean isRightConstant;

    public StringFeatureConstraint(
        LiteralConstraint left,
        LiteralConstraint right,
        String functionName,
        Boolean isRightConstant
    ) {
        this.left = left;
        this.right = right;
        this.functionName = functionName;
        this.isRightConstant = isRightConstant;
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
        //return new NumericFeatureValueFeatureCompareConstraint(left, right, inequalitySymbol) ;
        return null;
    }

    @Override
    public int hashCode(int level) {
        final int prime = 31;
        int result = prime * level + (left == null ? 0 : left.hashCode());
        result = prime * result + (right == null ? 0 : right.hashCode());
        result = prime * result + (functionName == null ? 0 : functionName.hashCode());
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
        StringFeatureConstraint other = (StringFeatureConstraint) obj;
        return Objects.equals(functionName, other.functionName) && Objects.equals(left, other.left)
            && Objects.equals(right, other.right);
    }
}
