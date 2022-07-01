package de.vill.model;

import de.vill.config.Configuration;

public class CardinalityGroup extends Group {
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

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("[");
        if(getLowerBound().equals(getUpperBound())){
            result.append(getLowerBound());
        } else {
            result.append(getLowerBound());
            result.append("..");
            result.append(getUpperBound());
        }
        result.append("]");
        result.append(Configuration.NEWLINE);
        result.append(super.toString());
        return result.toString();
    }
}
