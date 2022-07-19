package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.expression.Expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DropAggregateFunction implements IConversionStrategy{
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.AGGREGATE_FUNCTION));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(FeatureModel featureModel) {

    }

    private void removeRecursivelyAllAggregateFunctions(FeatureModel featureModel){
        for(Constraint constraint : featureModel.getOwnConstraints()){

        }
    }

    private boolean constraintContainsAggregateFunction(Constraint constraint){
        return false;
    }

    private boolean expressionContainsAggregateFunction(Expression expression){
        return false;
    }
}
