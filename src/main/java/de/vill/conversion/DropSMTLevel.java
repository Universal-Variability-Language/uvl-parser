package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.Expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DropSMTLevel implements IConversionStrategy{
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.SMT_LEVEL));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(FeatureModel featureModel) {
        featureModel.getOwnConstraints().removeIf(x -> constraintContainsEquation(x));
    }

    private boolean constraintContainsEquation(Constraint constraint){
        if(constraint instanceof ExpressionConstraint){
            return true;
        }else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()){
                if(constraintContainsEquation(subConstraint)){
                    return true;
                }
            }
        }

        return false;
    }
}
