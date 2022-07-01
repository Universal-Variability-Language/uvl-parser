package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

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
        result.append(Util.indentEachLine(getRootFeature().toString()));
        return result.toString();
    }
}
