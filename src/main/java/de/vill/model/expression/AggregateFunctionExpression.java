package de.vill.model.expression;

import static de.vill.util.Util.addNecessaryQuotes;

import de.vill.model.Feature;
import de.vill.util.Constants;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AggregateFunctionExpression extends Expression {
    public String getRootFeatureName() {
        return rootFeatureName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    private String rootFeatureName;

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    private String attributeName;

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }

    private Feature rootFeature;

    public AggregateFunctionExpression(String attributeName) {
        this.attributeName = attributeName;
    }

    public AggregateFunctionExpression(String rootFeatureName, String attributeName) {
        this(attributeName);
        this.rootFeatureName = rootFeatureName;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        return toString(withSubmodels, "aggregateFunction", currentAlias);
    }

    @Override
    public String getReturnType() {
        // implement in children
        return Constants.NUMBER;
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

    @Override
    public void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression) {

    }

    @Override
    public double evaluate(Set<Feature> selectedFeatures) {
        // TODO not necessary for now
        return 0;
    }

    protected String toString(boolean withSubmodels, String functionName, String currentAlias) {
        final StringBuilder result = new StringBuilder();
        result.append(functionName).append("(");

        if (getRootFeature() != null) {
            if (withSubmodels) {
                result.append(addNecessaryQuotes(getRootFeature().getFullReference()));
            } else {
                result.append(addNecessaryQuotes(getRootFeatureName()));
            }
        }

        if (getAttributeName() != null) {
            if (getRootFeature() != null) {
                result.append(", ");
            }
            result.append(addNecessaryQuotes(getAttributeName()));
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(attributeName);
        return result;
    }

    @Override
    public int hashCode(int level) {
        return 31 * level + (attributeName == null ? 0 : attributeName.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AggregateFunctionExpression other = (AggregateFunctionExpression) obj;
        return Objects.equals(attributeName, other.attributeName);
    }
}
