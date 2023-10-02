package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LengthAggregateFunctionExpression;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DropStringConstraints implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.STRING_CONSTRAINTS));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        featureModel.getOwnConstraints().removeIf(this::constraintContainsStringAggregateFunction);
    }

    private boolean constraintContainsStringAggregateFunction(final Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (final Expression subExpression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (this.expressionContainsStringAggregateFunction(subExpression)) {
                    return true;
                }
            }
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (this.constraintContainsStringAggregateFunction(subConstraint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean expressionContainsStringAggregateFunction(final Expression expression) {
        if (expression instanceof LengthAggregateFunctionExpression) {
            return true;
        } else {
            for (final Expression subExpression : expression.getExpressionSubParts()) {
                if (this.expressionContainsStringAggregateFunction(subExpression)) {
                    return true;
                }
            }
        }
        return false;
    }
}
