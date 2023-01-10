package de.vill.model.expression;

import de.vill.model.Feature;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NumberExpression extends Expression {
    public double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    private double number;

    public NumberExpression(double number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return toString(true, "");
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        if (number == Math.floor(number) && !Double.isInfinite(number)) {
            return Integer.toString((int) number);
        }
        return Double.toString(number);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {

    }

    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        return number;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(number);
        return result;
    }

    @Override
    public int hashCode(int level) {
        return 31 * level + Double.hashCode(number);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        NumberExpression other = (NumberExpression) obj;
        return Double.doubleToLongBits(number) == Double.doubleToLongBits(other.number);
    }
}
