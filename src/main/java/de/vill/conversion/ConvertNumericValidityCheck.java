package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.NumericFeatureConstraint;
import de.vill.model.typelevel.NumericFeatureAssignmentConstraint;
import de.vill.model.typelevel.NumericFeatureEqualsConstraint;
import de.vill.model.typelevel.NumericFeatureGreaterConstraint;
import de.vill.model.typelevel.NumericFeatureGreaterEqualsConstraint;
import de.vill.model.typelevel.NumericFeatureLowerConstraint;
import de.vill.model.typelevel.NumericFeatureLowerEqualsConstraint;
import de.vill.model.typelevel.NumericFeatureNotEqualsConstraint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConvertNumericValidityCheck implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.NUMERIC_VALIDITY_CHECK));
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
        if (constraint instanceof NumericFeatureConstraint) {
            processConstraint(fm, constraint);
        } else {
            constraint.getConstraintSubParts().forEach(c -> processConstraint(fm, c));
        }
    }

    private void processConstraint(FeatureModel fm, Constraint constraint) {
        if (constraint instanceof NumericFeatureAssignmentConstraint) {

        } else if (constraint instanceof NumericFeatureEqualsConstraint) {

        } else if (constraint instanceof NumericFeatureNotEqualsConstraint) {

        } else if (constraint instanceof NumericFeatureGreaterConstraint) {

        } else if (constraint instanceof NumericFeatureGreaterEqualsConstraint) {

        } else if (constraint instanceof NumericFeatureLowerConstraint) {

        } else if (constraint instanceof NumericFeatureLowerEqualsConstraint) {

        }
    }

    private void findAndAddAttribute(FeatureModel fm, Feature feature, Attribute attribute) {

    }
}
