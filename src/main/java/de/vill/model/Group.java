package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.LinkedList;
import java.util.List;

public class Group {
    public enum GroupType {
        OR,
        ALTERNATIVE,
        MANDATORY,
        OPTIONAL,
        CARDINALITY
    }

    public final GroupType GROUPTYPE;
    List<Feature> features;

    private String lowerBound;
    private String upperBound;

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public Group(GroupType groupType){
        this.GROUPTYPE = groupType;
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
        return toString(true, "");
    }

    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        switch (GROUPTYPE){
            case OR:
                result.append("or");
                break;
            case ALTERNATIVE:
                result.append("alternative");
                break;
            case OPTIONAL:
                result.append("optional");
                break;
            case MANDATORY:
                result.append("mandatory");
                break;
            case CARDINALITY:
                result.append("[");
                if(getLowerBound().equals(getUpperBound())){
                    result.append(getLowerBound());
                } else {
                    result.append(getLowerBound());
                    result.append("..");
                    result.append(getUpperBound());
                }
                result.append("]");
        }
        result.append(Configuration.NEWLINE);
        for(Feature feature : features){
            result.append(Util.indentEachLine(feature.toString(withSubmodels, currentAlias)));
        }

        return result.toString();
    }
}
