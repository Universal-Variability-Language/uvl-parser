package de.vill.model.expression;

import de.vill.model.Attribute;
import de.vill.model.Feature;

import java.util.Arrays;
import java.util.List;

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
        return toString(true, "");
    }

    public String toString(boolean withSubmodels, String currentAlias){
        if(getFeature() == null){
            return getAttributeName();
        }else if(withSubmodels){
            return getFeature().getFullReference() + "." + getAttributeName();
        }else {
            return feature.getReferenceFromSpecificSubmodel(currentAlias) + "." + getAttributeName();
        }
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {

    }
}
