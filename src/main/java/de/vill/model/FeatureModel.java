package de.vill.model;

import de.vill.config.Configuration;

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
        result.append(Configuration.NEWLINE);
        String[] rootFeatureLines = getRootFeature().toString().split(Configuration.NEWLINE);
        for(String line : rootFeatureLines){
            result.append(Configuration.TABULATOR);
            result.append(line);
            result.append(Configuration.NEWLINE);
        }

        return result.toString();
    }
}
