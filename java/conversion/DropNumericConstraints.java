package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.CeilAggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.FloorAggregateFunctionExpression;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DropNumericConstraints implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.NUMERIC_CONSTRAINTS));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        featureModel.getOwnConstraints().removeIf(this::constraintContainsNumericAggregateFunction);
    }

    private boolean constraintContainsNumericAggregateFunction(final Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (final Expression subExpression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (this.expressionContainsNumericAggregateFunction(subExpression)) {
                    return true;
                }
            }
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (this.constraintContainsNumericAggregateFunction(subConstraint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean expressionContainsNumericAggregateFunction(final Expression expression) {
        if (expression instanceof FloorAggregateFunctionExpression || expression instanceof CeilAggregateFunctionExpression) {
            return true;
        } else {
            for (final Expression subExpression : expression.getExpressionSubParts()) {
                if (this.expressionContainsNumericAggregateFunction(subExpression)) {
                    return true;
                }
            }
        }
        return false;
    }
}