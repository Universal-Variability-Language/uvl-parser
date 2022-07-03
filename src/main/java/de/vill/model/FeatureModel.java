package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

public class FeatureModel {
    private Feature rootFeature;
    private String namespace;

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }
    public String getNamespace() {
        return namespace;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        if(namespace != null) {
            result.append("namespace ");
            result.append(namespace);
            result.append(Configuration.NEWLINE);
            result.append(Configuration.NEWLINE);
        }
        if(rootFeature != null) {
            result.append("features");
            result.append(Configuration.NEWLINE);
            result.append(Util.indentEachLine(getRootFeature().toString()));
        }
        return result.toString();
    }
}
