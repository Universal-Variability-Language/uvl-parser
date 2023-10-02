package de.vill.conversion;

import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DropFeatureCardinality implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.FEATURE_CARDINALITY));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>();
    }

    @Override
    public void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel) {
        removeFeatureCardinalityRecursively(featureModel.getRootFeature());
    }

    private void removeFeatureCardinalityRecursively(Feature feature) {
        if (!(feature.getUpperBound() == null && feature.getLowerBound() == null)) {
            feature.setUpperBound(null);
            feature.setLowerBound(null);
        }
        for (Group group : feature.getChildren()) {
            for (Feature childFeature : group.getFeatures()) {
                //stop when feature is submodelroot to only consider this featuremodel and no submodels
                if (!feature.isSubmodelRoot()) {
                    removeFeatureCardinalityRecursively(childFeature);
                }
            }
        }
    }
}
