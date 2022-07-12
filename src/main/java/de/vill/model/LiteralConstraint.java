package de.vill.model;

public class LiteralConstraint extends Constraint{
    private String literal;

    private Import relatedImport;
    private String nameSpace;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public Import getRelatedImport() {
        return relatedImport;
    }

    public void setRelatedImport(Import relatedImport) {
        this.relatedImport = relatedImport;
    }

    private Feature feature;

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public LiteralConstraint(String literal){
        this.literal = literal;
    }

    public String getLiteral(){
        return literal;
    }

    @Override
    public String toString(){
        return toString(true);
    }
    public String toString(boolean withSubmodels){
        if(getFeature() != null && withSubmodels){
            if(getFeature().isImported()){
                return getFeature().getNAME();
            }else {
                if(getFeature().getNameSpace() != null) {
                    return getFeature().getNameSpace() + "." + getFeature().getNAME();
                }else {
                    return getFeature().getNAME();
                }
            }
        }else {
            return getLiteral();
        }
    }
}
