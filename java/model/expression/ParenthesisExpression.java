package de.vill.model.expression;

import de.vill.model.Feature;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ParenthesisExpression extends Expression {
    private Expression content;

    public ParenthesisExpression(Expression content) {
        this.content = content;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return "(" +
            content.toString(withSubmodels, currentAlias) +
            ")";
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Collections.singletonList(content);
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {
        if (content == oldSubExpression) {
            content = newSubExpression;
        }
    }

    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        return content.evaluate(selectedFeatures);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(content);
        return result;
    }

    @Override
    public int hashCode(int level) {
        return 31 * level + (content == null ? 0 : content.hashCode(1 + level));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ParenthesisExpression other = (ParenthesisExpression) obj;
        return Objects.equals(content, other.content);
    }

    @Override
    public String getReturnType() {
        return content.getReturnType();
    }
}
