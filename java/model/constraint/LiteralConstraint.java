package de.vill.model.constraint;

import de.vill.model.Feature;
import de.vill.model.Import;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static de.vill.util.Util.addNecessaryQuotes;

public class LiteralConstraint extends Constraint {
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

    public LiteralConstraint(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        if (getFeature() == null) {
            return addNecessaryQuotes(getLiteral());
        }
        if (withSubmodels) {
            return addNecessaryQuotes(getFeature().getFullReference());
        }
        return addNecessaryQuotes(feature.getReferenceFromSpecificSubmodel(currentAlias));
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Arrays.asList();
    }

    @Override
    public void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint) {
        // no sub constraints
    }

    @Override
    public Constraint clone() {
        LiteralConstraint newConstraint = new LiteralConstraint(getLiteral());
        newConstraint.setFeature(getFeature());
        newConstraint.setNameSpace(getNameSpace());
        newConstraint.setRelatedImport(getRelatedImport());
        return newConstraint;
    }

    @Override
    public int hashCode(int level) {
        return 31 * level + (feature == null ? 0 : literal.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LiteralConstraint other = (LiteralConstraint) obj;
        return Objects.equals(literal, other.literal);
    }
}
