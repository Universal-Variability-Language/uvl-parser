package de.vill.exception;

import de.vill.model.FeatureModel;

public class FaultyReference extends ParseError {
    private FeatureModel featureModel;

    public FaultyReference(FeatureModel featureModel) {
        super("Faulty feature names");
        this.featureModel = featureModel;
    }

    public FeatureModel getFeatureModel() {
        return this.featureModel;
    }
}
