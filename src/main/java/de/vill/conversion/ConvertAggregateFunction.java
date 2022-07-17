package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;

import java.util.Set;

public class ConvertAggregateFunction implements IConversionStrategy{
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return null;
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return null;
    }

    @Override
    public void convertFeatureModel(FeatureModel featureModel) {

    }
}
