package de.vill.model.expression;

import de.vill.model.Feature;
import de.vill.model.constraint.ParenthesisConstraint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ParenthesisExpression extends Expression{
    private Expression content;

    public ParenthesisExpression(Expression content){
        this.content = content;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(content.toString(withSubmodels, currentAlias));
        result.append(")");
        return result.toString();
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Collections.singletonList(content);
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {
        if(content == oldSubExpression){
            content = newSubExpression;
        }
    }

    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        return content.evaluate(selectedFeatures);
    }
}
