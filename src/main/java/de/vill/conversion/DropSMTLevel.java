package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DropSMTLevel implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.ARITHMETIC_LEVEL));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        featureModel.getOwnConstraints().removeIf(x -> this.constraintContainsEquation(x));
    }

    private boolean constraintContainsEquation(final Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            return true;
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (this.constraintContainsEquation(subConstraint)) {
                    return true;
                }
            }
        }

        return false;
    }
}
