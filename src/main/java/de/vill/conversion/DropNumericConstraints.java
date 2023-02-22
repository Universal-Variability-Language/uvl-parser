package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.FeatureType;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;
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
        featureModel.getOwnConstraints().removeIf(this::constraintContainsNumericFunctions);
    }

    private boolean constraintContainsNumericFunctions(Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (Expression subExpression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expressionContainsNumericFunction(subExpression)) {
                    return true;
                }
            }
        } else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (constraintContainsNumericFunctions(subConstraint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean expressionContainsNumericFunction(Expression expression) {
        if (FeatureType.INT.equals(((LiteralExpression) expression).getFeature().getFeatureType()) ||
            FeatureType.REAL.equals(((LiteralExpression) expression).getFeature().getFeatureType())) {
            return true;
        } else {
            for (Expression subExpression : expression.getExpressionSubParts()) {
                if (expressionContainsNumericFunction(subExpression)) {
                    return true;
                }
            }
        }
        return false;
    }
}
