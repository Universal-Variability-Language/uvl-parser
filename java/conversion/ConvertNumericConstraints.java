package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.CeilAggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.FloorAggregateFunctionExpression;
import de.vill.model.expression.LiteralExpression;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConvertNumericConstraints implements IConversionStrategy {
    private final Map<String, Map<String, Attribute>> featuresToBeUpdated = new HashMap<>();
    private FeatureModel rootFeatureModel;

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.NUMERIC_CONSTRAINTS));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        this.rootFeatureModel = rootFeatureModel;
        rootFeatureModel.getOwnConstraints().forEach(this::convertNumericAggregateFunctionInExpressionConstraint);
        this.traverseFeatures(rootFeatureModel.getRootFeature());
    }

    private void convertNumericAggregateFunctionInExpressionConstraint(final Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (final Expression expression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expression instanceof FloorAggregateFunctionExpression || expression instanceof CeilAggregateFunctionExpression) {
                    this.replaceNumericAggregateFunctionInExpressionConstraint((ExpressionConstraint) constraint,
                        (AggregateFunctionExpression) expression);
                } else {
                    this.convertNumericAggregateFunctionInExpression(expression);
                }
            }
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                this.convertNumericAggregateFunctionInExpressionConstraint(subConstraint);
            }
        }
    }

    private void convertNumericAggregateFunctionInExpression(final Expression expression) {
        for (final Expression subExpression : expression.getExpressionSubParts()) {
            if (subExpression instanceof FloorAggregateFunctionExpression || subExpression instanceof CeilAggregateFunctionExpression) {
                this.replaceNumericAggregateFunctionInExpression(expression, (AggregateFunctionExpression) subExpression);
            } else {
                this.convertNumericAggregateFunctionInExpression(subExpression);
            }
        }
    }

    private void replaceNumericAggregateFunctionInExpression(final Expression parentExpression,
                                                             final AggregateFunctionExpression aggregateFunctionExpression) {
        String attributeName = "";
        if (aggregateFunctionExpression instanceof FloorAggregateFunctionExpression) {
            attributeName = "type_level_value_floor";
            this.addAttribute(
                aggregateFunctionExpression,
                attributeName,
                this.computeAttributeValue(
                    this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()),
                    aggregateFunctionExpression
                )
            );
        } else if (aggregateFunctionExpression instanceof CeilAggregateFunctionExpression) {
            attributeName = "type_level_value_ceil";
            this.addAttribute(
                aggregateFunctionExpression,
                attributeName,
                this.computeAttributeValue(
                    this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()),
                    aggregateFunctionExpression
                )
            );
        }
        Feature relevantFeature = rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName());
        final Expression newExpression = new LiteralExpression(relevantFeature, attributeName);
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void replaceNumericAggregateFunctionInExpressionConstraint(final ExpressionConstraint parentExpression,
                                                                       final AggregateFunctionExpression aggregateFunctionExpression) {
        String attributeName = "";
        if (aggregateFunctionExpression instanceof FloorAggregateFunctionExpression) {
            attributeName = "type_level_value_floor";
            this.addAttribute(
                aggregateFunctionExpression,
                attributeName,
                this.computeAttributeValue(
                    this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()),
                    aggregateFunctionExpression
                )
            );
        } else if (aggregateFunctionExpression instanceof CeilAggregateFunctionExpression) {
            attributeName = "type_level_value_ceil";
            this.addAttribute(
                aggregateFunctionExpression,
                attributeName,
                this.computeAttributeValue(
                    this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()),
                    aggregateFunctionExpression
                )
            );
        }
        Feature relevantFeature = rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName());
        final Expression newExpression = new LiteralExpression(relevantFeature, attributeName);
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

    private void addAttribute(final AggregateFunctionExpression aggregateFunctionExpression, final String key, final Object value) {
        final Map<String, Attribute> currentAttributes =
            this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()).getAttributes();
        currentAttributes.put(
            key,
            new Attribute<>(key, value)
        );
        this.featuresToBeUpdated.put(aggregateFunctionExpression.getRootFeatureName(), currentAttributes);
    }

    private Double computeAttributeValue(final Feature feature, final AggregateFunctionExpression expression) {
        if (feature.getAttributes().containsKey("type_level_value")) {
            if (expression instanceof FloorAggregateFunctionExpression) {
                return Math.floor(
                    Double.parseDouble(feature.getAttributes().get("type_level_value").getValue().toString())
                );
            } else if (expression instanceof CeilAggregateFunctionExpression) {
                return Math.ceil(
                    Double.parseDouble(feature.getAttributes().get("type_level_value").getValue().toString())
                );
            }
        }

        return 0.0;
    }
}
