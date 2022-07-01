package de.vill.model;

public class FeatureModel {
    private Feature rootFeature;

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }

    @Override
    public String toString(){
        return rootFeature.toString();
    }
}
