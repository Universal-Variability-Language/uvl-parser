package de.vill.model.expression;

import de.vill.model.Feature;
import java.util.Collections;
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
        return value;
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Collections.emptyList();
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {
        if (oldSubExpression instanceof StringExpression && ((StringExpression) oldSubExpression).getString().equals(value) &&
            newSubExpression instanceof StringExpression) {
            value = ((StringExpression) newSubExpression).value;
        }
    }

    //TODO:
    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(value);
        return result;
    }

    @Override
    public int hashCode(int level) {
        return 31 * level + Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StringExpression other = (StringExpression) obj;
        return Objects.equals(value, other.value);
    }
}
