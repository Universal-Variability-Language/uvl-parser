package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.StringFeatureConstraint;
import de.vill.model.typelevel.StringFeatureAssignmentConstraint;
import de.vill.model.typelevel.StringFeatureEqualsConstraint;
import de.vill.model.typelevel.StringFeatureLengthConstraint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConvertStringAggregateFunction implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.STRING_AGGREGATE_FUNCTION));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        featureModel.getOwnConstraints().forEach(constraint -> traverseConstraint(featureModel, constraint));
    }

    private void traverseConstraint(FeatureModel fm, Constraint constraint) {
        if (constraint instanceof StringFeatureConstraint) {
            processConstraint(fm, constraint);
        } else {
            constraint.getConstraintSubParts().forEach(c -> processConstraint(fm, c));
        }
    }

    private void processConstraint(FeatureModel fm, Constraint constraint) {
        if (constraint instanceof StringFeatureAssignmentConstraint) {

        } else if (constraint instanceof StringFeatureEqualsConstraint) {

        } else if (constraint instanceof StringFeatureLengthConstraint) {

        }
    }

    private void findAndAddAttribute(FeatureModel fm, Feature feature, Attribute attribute) {

    }
}
