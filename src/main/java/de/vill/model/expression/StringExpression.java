package de.vill.model.expression;

import de.vill.model.Feature;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class StringExpression extends Expression {
    public String getString() {
        return value;
    }

    public void setString(String value) {
        this.value = value;
    }

    private String value;

    public StringExpression(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return toString(true, "");
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return null;
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return null;
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {

    }

    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        return 0;
    }

    @Override
    public int hashCode(int level) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

//    @Override
//    public String toString(boolean withSubmodels, String currentAlias) {
//        if (number == Math.floor(number) && !Double.isInfinite(number)) {
//            return Integer.toString((int) number);
//        }
//        return Double.toString(number);
//    }
//
//    @Override
//    public List<Expression> getExpressionSubParts() {
//        return Arrays.asList();
//    }
//
//    @Override
//    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {
//
//    }
//
//    @Override
//    public double evaluate(Set<Feature> selectedFeatures) {
//        return number;
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = super.hashCode();
//        result = prime * result + Objects.hash(number);
//        return result;
//    }
//
//    @Override
//    public int hashCode(int level) {
//        return 31 * level + Double.hashCode(number);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if ((obj == null) || (getClass() != obj.getClass())) {
//            return false;
//        }
//        StringExpression other = (StringExpression) obj;
//        return Double.doubleToLongBits(number) == Double.doubleToLongBits(other.number);
//    }
}
