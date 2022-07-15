package de.vill.model;

public class AndConstraint extends Constraint{
    private Constraint left;
    private Constraint right;

    public AndConstraint(Constraint left, Constraint right){
        this.left = left;
        this.right = right;
    }

    public Constraint getLeft() {
        return left;
    }

    public Constraint getRight() {
        return right;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        result.append(left.toString(withSubmodels, currentAlias));
        result.append(" & ");
        result.append(right.toString(withSubmodels, currentAlias));
        return result.toString();
    }
}
