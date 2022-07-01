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

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();

        for(Feature feature : features){
            result.append(Util.indentEachLine(feature.toString()));
        }

        return result.toString();
    }
}
