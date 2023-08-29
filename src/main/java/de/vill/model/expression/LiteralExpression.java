package de.vill.model.expression;

import static de.vill.util.Util.addNecessaryQuotes;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureType;
import de.vill.util.Constants;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LiteralExpression extends Expression {
    private String content;
    private String attributeName;
    private String featureName;
    private Feature feature;
    private Boolean boolValue; // just used for bool constants

    public LiteralExpression(Boolean value) {
        this.boolValue = value;
    }

    public LiteralExpression(final Feature feature, final String attributeName) {
        this.feature = feature;
        this.featureName = feature.getFeatureName();
        this.attributeName = attributeName;
        this.content = featureName + "." + attributeName;
    }

    public LiteralExpression(final String content) {
        this.content = content;
        final String[] contentSplit = content.split("\\.");
        this.featureName = contentSplit[0];
        this.attributeName = contentSplit.length == 2 ? contentSplit[1] : null;
    }

    public String getContent() {
        return this.content;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public Attribute getAttribute() {
        if (this.feature == null) {
            return null;
        }
        return this.getFeature().getAttributes().get(this.attributeName);
    }

    public Feature getFeature() {
        return this.feature;
    }

    public void setFeature(final Feature feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return this.toString(true, "");
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        if (this.getFeature() == null) {
            if (boolValue != null) {
                return String.valueOf(boolValue);
            }
            return addNecessaryQuotes(this.getContent());
        }
        if (withSubmodels) {
            return addNecessaryQuotes(this.getFeature().getFullReference()
                    + (this.getAttributeName() != null ? "." + this.getAttributeName() : ""));
        }
        return addNecessaryQuotes(
                this.feature.getReferenceFromSpecificSubmodel(currentAlias)
                        + (this.getAttributeName() != null ? "." + this.getAttributeName() : ""));
    }

    @Override
    public String getReturnType() {
        if (this.feature != null) {
            if (this.attributeName != null) {
                return getAttribute().getType();
            } else if (FeatureType.STRING.equals(this.feature.getFeatureType())) {
                return Constants.STRING;
            } else if (FeatureType.BOOL.equals(this.feature.getFeatureType())) {
                return Constants.BOOLEAN;
            } else if (this.boolValue != null) {
                return Constants.BOOLEAN;
            } else {
                return Constants.NUMBER;
            }
        }

        return "";
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Collections.emptyList();
    }

    @Override
    public void replaceExpressionSubPart(final Expression oldSubExpression, final Expression newSubExpression) {
        if (oldSubExpression instanceof LiteralExpression
                && ((LiteralExpression) oldSubExpression).getContent().equals(this.content) &&
                newSubExpression instanceof LiteralExpression) {
            this.content = ((LiteralExpression) newSubExpression).content;
            this.featureName = ((LiteralExpression) newSubExpression).featureName;
            this.attributeName = ((LiteralExpression) newSubExpression).attributeName;
            this.feature = ((LiteralExpression) newSubExpression).feature;
        }
    }

    @Override
    public double evaluate(final Set<Feature> selectedFeatures) {
        if (boolValue != null || !this.feature.getAttributes().containsKey(this.attributeName)) {
            return 0d;
        }
        final Object attributeValue = this.feature.getAttributes().get(this.attributeName).getValue();
        if (attributeValue instanceof Integer) {
            return ((Integer) attributeValue).doubleValue();
        }
        if (attributeValue instanceof Long) {
            return ((Long) attributeValue).doubleValue();
        }
        return (double) attributeValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.attributeName);
        return result;
    }

    @Override
    public int hashCode(final int level) {
        return 31 * level + (this.attributeName == null ? 0 : this.attributeName.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final LiteralExpression other = (LiteralExpression) obj;
        return Objects.equals(this.attributeName, other.attributeName);
    }

}
