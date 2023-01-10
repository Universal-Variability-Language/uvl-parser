package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DropAggregateFunction implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.AGGREGATE_FUNCTION));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel) {
        featureModel.getOwnConstraints().removeIf(x -> constraintContainsAggregateFunction(x));
    }

    private boolean constraintContainsAggregateFunction(Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (Expression subExpression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expressionContainsAggregateFunction(subExpression)) {
                    return true;
                }
            }
        } else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (constraintContainsAggregateFunction(subConstraint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean expressionContainsAggregateFunction(Expression expression) {
        if (expression instanceof AggregateFunctionExpression) {
            return true;
        } else {
            for (Expression subExpression : expression.getExpressionSubParts()) {
                if (expressionContainsAggregateFunction(subExpression)) {
                    return true;
                }
            }
        }
        return false;
    }
}
