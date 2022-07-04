package de.vill.model;

public class ImplicationConstraint extends Constraint{
    private Constraint left;
    private Constraint right;

    public ImplicationConstraint(Constraint left, Constraint right){
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
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(left);
        result.append(" => ");
        result.append(right);
        return result.toString();
    }
}
