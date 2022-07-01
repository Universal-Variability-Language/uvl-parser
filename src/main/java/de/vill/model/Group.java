package de.vill.model;

import de.vill.config.Configuration;

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
            String[] rootFeatureLines = feature.toString().split(Configuration.NEWLINE);
            for(String line : rootFeatureLines){
                result.append(Configuration.TABULATOR);
                result.append(line);
                result.append(Configuration.NEWLINE);
            }
        }

        return result.toString();
    }
}
