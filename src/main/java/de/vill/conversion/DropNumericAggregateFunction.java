package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.NumericFeatureConstraint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DropNumericAggregateFunction implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.NUMERIC_AGGREGATE_FUNCTION));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        featureModel.getOwnConstraints().removeIf(x -> x instanceof NumericFeatureConstraint);
    }
}