package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.FeatureType;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LengthAggregateFunctionExpression;
import de.vill.model.expression.LiteralExpression;
import de.vill.model.expression.StringExpression;
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
        featureModel.getOwnConstraints().removeIf(this::constraintContainsStringFunctions);
    }

    private boolean constraintContainsStringFunctions(Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (Expression subExpression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expressionContainsStringFunction(subExpression)) {
                    return true;
                }
            }
        } else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (constraintContainsStringFunctions(subConstraint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean expressionContainsStringFunction(Expression expression) {
        if (expression instanceof LengthAggregateFunctionExpression || expression instanceof StringExpression) {
            return true;
        } else if (FeatureType.STRING.equals(((LiteralExpression) expression).getFeature().getFeatureType())) {
            return true;
        } else {
            for (Expression subExpression : expression.getExpressionSubParts()) {
                if (expressionContainsStringFunction(subExpression)) {
                    return true;
                }
            }
        }
        return false;
    }
}
