package de.vill.model.constraint;

import de.vill.model.Feature;
import de.vill.model.Import;

import java.util.Arrays;
import java.util.List;

import static de.vill.util.Util.addNecessaryQuotes;

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

    public String toString(boolean withSubmodels, String currentAlias){
        if(getFeature() == null){
            return addNecessaryQuotes(getLiteral());
        }else if(withSubmodels){
            return addNecessaryQuotes(getFeature().getFullReference());
        }else {
            return addNecessaryQuotes(feature.getReferenceFromSpecificSubmodel(currentAlias));
        }
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Arrays.asList();
    }
}
