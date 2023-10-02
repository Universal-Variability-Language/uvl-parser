package de.vill.model.expression;

import de.vill.model.Feature;
import de.vill.util.Constants;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DivExpression extends Expression {
    private Expression left;
    private Expression right;

    public DivExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return left.toString(withSubmodels, currentAlias) +
            " / " +
            right.toString(withSubmodels, currentAlias);
    }

    @Override
    public String getReturnType() {
        return Constants.NUMBER;
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList(left, right);
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {
        if (left == oldSubExpression) {
            left = newSubExpression;
        } else if (right == oldSubExpression) {
            right = newSubExpression;
        }
    }

    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        double leftResult;
        if (left instanceof LiteralExpression && !selectedFeatures.contains(((LiteralExpression) left).getFeature())) {
            leftResult = 0;
        } else {
            leftResult = left.evaluate(selectedFeatures);
        }
        double rightResult;
        if (right instanceof LiteralExpression
            && !selectedFeatures.contains(((LiteralExpression) right).getFeature())) {
            rightResult = 1;
        } else {
            rightResult = right.evaluate(selectedFeatures);
        }
        return leftResult + rightResult;
    }

    @Override
    public int hashCode(int level) {
        final int prime = 31;
        int result = prime * level + (left == null ? 0 : left.hashCode(1 + level));
        result = prime * result + (right == null ? 0 : right.hashCode(1 + level));
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
        DivExpression other = (DivExpression) obj;
        return Objects.equals(left, other.left) && Objects.equals(right, other.right);
    }
}
