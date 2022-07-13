package de.vill.model;

public class LiteralExpression extends Expression{
    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    private String attributeName;

    public Attribute getAttribute() {
        return getFeature().getAttributes().get(attributeName);
    }


    private Feature feature;

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }


    public LiteralExpression(String content){
        this.attributeName = content;
    }

    @Override
    public String toString(){
        return toString(true);
    }

    public String toString(boolean withSubmodels){
        if(getFeature() != null && withSubmodels){
            if(getFeature().isImported()){
                return getFeature().getNAME() + "." + getAttributeName();
            }else {
                if(getFeature().getNameSpace() != null) {
                    return getFeature().getNameSpace() + "." + getFeature().getNAME() + "." + getAttributeName();
                }else {
                    return getFeature().getNAME() + "." + getAttributeName();
                }
            }
        }else {
            return getFeature().getNAME() + "." + getAttributeName();
        }
    }
}
