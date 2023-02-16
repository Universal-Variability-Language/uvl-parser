package de.vill.conversion;

import de.vill.exception.ParseError;
import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.EqualEquationConstraint;
import de.vill.model.constraint.StringFeatureConstraint;
import de.vill.model.expression.LiteralExpression;
import de.vill.model.typelevel.StringFeatureEqualsConstraint;
import de.vill.model.typelevel.StringFeatureLengthConstraint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConvertStringAggregateFunction implements IConversionStrategy {
    private final Map<String, Map<String, Attribute>> featuresToBeUpdated = new HashMap<>();

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
        traverseFeatures(fm.getRootFeature());
    }

    private Constraint processSimpleConstraint(FeatureModel fm, Constraint constraint) {
        Boolean isRightConstant = ((StringFeatureConstraint) constraint).getIsRightConstant();
        if (constraint instanceof StringFeatureEqualsConstraint) {
            return new EqualEquationConstraint(
                new LiteralExpression(((StringFeatureEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_value"),
                new LiteralExpression(
                    ((StringFeatureEqualsConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_value"))
            );
        } else if (constraint instanceof StringFeatureLengthConstraint) {
            Map<String, Attribute> currentAttributes =
                fm.getFeatureMap().get(((StringFeatureLengthConstraint) constraint).getLeft().getLiteral()).getAttributes();
            currentAttributes.put(
                "type_level_value_length",
                new Attribute<>(
                    "type_level_value_length",
                    getStringLength(fm.getFeatureMap().get(((StringFeatureLengthConstraint) constraint).getLeft().getLiteral())).toString()
                )
            );
            featuresToBeUpdated.put(((StringFeatureLengthConstraint) constraint).getLeft().getLiteral(), currentAttributes);
            if (!((StringFeatureLengthConstraint) constraint).getIsRightConstant()) {
                currentAttributes = fm.getFeatureMap().get(((StringFeatureLengthConstraint) constraint).getRight().getLiteral()).getAttributes();
                currentAttributes.put(
                    "type_level_value_length",
                    new Attribute<>(
                        "type_level_value_length",
                        getStringLength(fm.getFeatureMap().get(((StringFeatureLengthConstraint) constraint).getRight().getLiteral())).toString()
                    )
                );
                featuresToBeUpdated.put(((StringFeatureLengthConstraint) constraint).getRight().getLiteral(), currentAttributes);
            }
            return new EqualEquationConstraint(
                new LiteralExpression(((StringFeatureLengthConstraint) constraint).getLeft().getLiteral() + ".type_level_value_length"),
                new LiteralExpression(
                    ((StringFeatureLengthConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_value_length"))
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

    private void traverseFeatures(final Feature feature) {
        if (featuresToBeUpdated.containsKey(feature.getFeatureName())) {
            feature.getAttributes().putAll(featuresToBeUpdated.get(feature.getFeatureName()));
        }

        for (final Group group : feature.getChildren()) {
            for (final Feature subFeature : group.getFeatures()) {
                this.traverseFeatures(subFeature);
            }
        }
    }

    private Integer getStringLength(Feature feature) {
        if (feature.getAttributes().containsKey("type_level_value")) {
            return feature.getAttributes().get("type_level_value").getValue().toString().length();
        }

        return 0;
    }
}
