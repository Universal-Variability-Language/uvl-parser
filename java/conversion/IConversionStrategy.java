package de.vill.conversion;

import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;

import java.util.Set;

public interface IConversionStrategy {
    Set<LanguageLevel> getLevelsToBeRemoved();

    Set<LanguageLevel> getTargetLevelsOfConversion();

    void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel);

}
