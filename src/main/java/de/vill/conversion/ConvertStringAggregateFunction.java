package de.vill.conversion;

import de.vill.exception.ParseError;
import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.AssignmentEquationConstraint;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.EqualEquationConstraint;
import de.vill.model.constraint.StringFeatureConstraint;
import de.vill.model.expression.LiteralExpression;
import de.vill.model.typelevel.StringFeatureAssignmentConstraint;
import de.vill.model.typelevel.StringFeatureEqualsConstraint;
import de.vill.model.typelevel.StringFeatureLengthConstraint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
        processConstraints(featureModel);
    }

    private void processConstraints(FeatureModel fm) {
        List<Constraint> constraints = new ArrayList<>();
        for (Constraint constraint : fm.getOwnConstraints()) {
            if (constraint instanceof StringFeatureConstraint) {
                constraints.add(processSimpleConstraint(fm, constraint));
            } else {
                constraints.add(processCompoundConstraint(fm, constraint));
            }
        }
        if (constraints.size() != 0) {
            fm.getOwnConstraints().clear();
            fm.getOwnConstraints().addAll(constraints);
        }
    }

    private Constraint processSimpleConstraint(FeatureModel fm, Constraint constraint) {
        Boolean isRightConstant = ((StringFeatureConstraint) constraint).getIsRightConstant();
        if (constraint instanceof StringFeatureAssignmentConstraint) {
            return new AssignmentEquationConstraint(
                new LiteralExpression(((StringFeatureAssignmentConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                new LiteralExpression(
                    ((StringFeatureAssignmentConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_default_value"))
            );
        } else if (constraint instanceof StringFeatureEqualsConstraint) {
            return new EqualEquationConstraint(
                new LiteralExpression(((StringFeatureEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                new LiteralExpression(
                    ((StringFeatureEqualsConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_default_value"))
            );
        } else if (constraint instanceof StringFeatureLengthConstraint) {
            findFeatureAndAddAttribute(
                fm.getRootFeature(),
                fm.getFeatureMap().get(((StringFeatureLengthConstraint) constraint).getLeft().getLiteral()),
                new Attribute<Integer>("length", 0)
            );
            if (!((StringFeatureLengthConstraint) constraint).getIsRightConstant()) {
                findFeatureAndAddAttribute(
                    fm.getRootFeature(),
                    fm.getFeatureMap().get(((StringFeatureLengthConstraint) constraint).getRight().getLiteral()),
                    new Attribute<Integer>("length", 0)
                );
            }
            return new EqualEquationConstraint(
                new LiteralExpression(((StringFeatureLengthConstraint) constraint).getLeft().getLiteral() + ".length"),
                new LiteralExpression(
                    ((StringFeatureLengthConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".length"))
            );
        }

        // should not come in an ideal scenario
        throw new ParseError("constraint type not supported");
    }

    private Constraint processCompoundConstraint(FeatureModel fm, Constraint constraint) {
        if (constraint instanceof StringFeatureConstraint) {
            return processSimpleConstraint(fm, constraint);
        }

        constraint.getConstraintSubParts().forEach(c -> c.replaceConstraintSubPart(c, processCompoundConstraint(fm, c)));

        return constraint;
    }

    //TODO: fix
    private void findFeatureAndAddAttribute(Feature current, Feature feature, Attribute attribute) {
        if (current.getFeatureName().equals(feature.getFeatureName())) {
            feature.getAttributes().put(
                attribute.getName(),
                attribute
            );
        }

        for (final Group group : feature.getChildren()) {
            for (final Feature subFeature : group.getFeatures()) {
                this.findFeatureAndAddAttribute(subFeature, feature, attribute);
            }
        }
    }
}
