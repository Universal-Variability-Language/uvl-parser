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
        StringBuilder result = new StringBuilder();
        result.append("features");
        result.append("\n");
        String[] rootFeatureLines = getRootFeature().toString().split("\n");
        for(String line : rootFeatureLines){
            result.append("\t");
            result.append(line);
            result.append("\n");
        }

        return result.toString();
    }
}
