package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LengthAggregateFunctionExpression;
import de.vill.model.expression.LiteralExpression;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConvertStringConstraints implements IConversionStrategy {
    private final Map<String, Map<String, Attribute>> featuresToBeUpdated = new HashMap<>();
    private FeatureModel rootFeatureModel;

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.STRING_CONSTRAINTS));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        this.rootFeatureModel = rootFeatureModel;
        rootFeatureModel.getOwnConstraints().forEach(this::convertStringAggregateFunctionInExpressionConstraint);
        this.traverseFeatures(rootFeatureModel.getRootFeature());
    }

    private void convertStringAggregateFunctionInExpressionConstraint(final Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (final Expression expression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expression instanceof LengthAggregateFunctionExpression) {
                    this.replaceStringAggregateFunctionInExpressionConstraint((ExpressionConstraint) constraint,
                        (AggregateFunctionExpression) expression);
                } else {
                    this.convertStringAggregateFunctionInExpression(expression);
                }
            }
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                this.convertStringAggregateFunctionInExpressionConstraint(subConstraint);
            }
        }
    }

    private void convertStringAggregateFunctionInExpression(final Expression expression) {
        for (final Expression subExpression : expression.getExpressionSubParts()) {
            if (subExpression instanceof LengthAggregateFunctionExpression) {
                this.replaceStringAggregateFunctionInExpression(expression, (LengthAggregateFunctionExpression) subExpression);
            } else {
                this.convertStringAggregateFunctionInExpression(subExpression);
            }
        }
    }

    private void replaceStringAggregateFunctionInExpression(final Expression parentExpression,
                                                            final AggregateFunctionExpression aggregateFunctionExpression) {
        this.addAttribute(aggregateFunctionExpression);
        final Expression newExpression = new LiteralExpression(aggregateFunctionExpression.getRootFeatureName() + ".type_level_value_length");
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void replaceStringAggregateFunctionInExpressionConstraint(final ExpressionConstraint parentExpression,
                                                                      final AggregateFunctionExpression aggregateFunctionExpression) {
        this.addAttribute(aggregateFunctionExpression);
        final Expression newExpression = new LiteralExpression(aggregateFunctionExpression.getRootFeatureName() + ".type_level_value_length");
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void traverseFeatures(final Feature feature) {
        if (this.featuresToBeUpdated.containsKey(feature.getFeatureName())) {
            feature.getAttributes().putAll(this.featuresToBeUpdated.get(feature.getFeatureName()));
        }

        for (final Group group : feature.getChildren()) {
            for (final Feature subFeature : group.getFeatures()) {
                this.traverseFeatures(subFeature);
            }
        }
    }

    private void addAttribute(final AggregateFunctionExpression aggregateFunctionExpression) {
        final Map<String, Attribute> currentAttributes =
            this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()).getAttributes();
        currentAttributes.put(
            "type_level_value_length",
            new Attribute<>(
                "type_level_value_length",
                this.computeStringLength(
                    this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName())).toString()
            )
        );
        this.featuresToBeUpdated.put(aggregateFunctionExpression.getRootFeatureName(), currentAttributes);
    }

    private Integer computeStringLength(final Feature feature) {
        if (feature.getAttributes().containsKey("type_level_value")) {
            return feature.getAttributes().get("type_level_value").getValue().toString().length();
        }

        return 0;
    }
}
