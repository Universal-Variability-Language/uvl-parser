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
        final LiteralConstraint left,
        final LiteralConstraint right,
        final String functionName,
        final Boolean isRightConstant
    ) {
        this.left = left;
        this.right = right;
        this.functionName = functionName;
        this.isRightConstant = isRightConstant;
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
        result = prime * result + (this.functionName == null ? 0 : this.functionName.hashCode());
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
        final StringFeatureConstraint other = (StringFeatureConstraint) obj;
        return Objects.equals(this.functionName, other.functionName) && Objects.equals(this.left, other.left)
            && Objects.equals(this.right, other.right);
    }

    public LiteralConstraint getLeft() {
        return this.left;
    }

    public LiteralConstraint getRight() {
        return this.right;
    }

    public Boolean getIsRightConstant() {
        return this.isRightConstant;
    }
}
