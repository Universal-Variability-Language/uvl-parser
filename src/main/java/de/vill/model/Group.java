package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.LinkedList;
import java.util.List;

public abstract class Group {
    List<Feature> features;

    public Group(){
        features = new LinkedList<>();
    }

    public void addFeature(Feature feature){
        features.add(feature);
    }

    public List<Feature> getFeatures(){
        return features;
    }

    @Override
    public String toString(){
        return toString(true);
    }

    public String toString(boolean withSubmodels){
        StringBuilder result = new StringBuilder();

        for(Feature feature : features){
            result.append(Util.indentEachLine(feature.toString(withSubmodels)));
        }

        return result.toString();
    }
}
