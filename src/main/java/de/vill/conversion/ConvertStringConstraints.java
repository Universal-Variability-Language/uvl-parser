package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.EqualEquationConstraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LengthAggregateFunctionExpression;
import de.vill.model.expression.LiteralExpression;
import de.vill.model.expression.StringExpression;
import de.vill.util.Constants;

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
        rootFeatureModel.getOwnConstraints().removeIf(x -> containsStringEqualityExpression(x));
        this.traverseFeatures(rootFeatureModel.getRootFeature());
    }

    private Boolean containsStringEqualityExpression(final Constraint constraint) {
        if (constraint instanceof EqualEquationConstraint) {
            for (final Expression expression : ((EqualEquationConstraint)constraint).getExpressionSubParts()) {
                if (expression instanceof StringExpression) {
                    System.out.println("Warning: String equality constraint in line " + constraint.getLineNumber() + " was dropped.");
                    return true;
                }
            }
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                if (this.containsStringEqualityExpression(subConstraint)) {
                    return true;
                }
            }
        }
        return false;
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
        Feature relevantFeature = rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName());
        final Expression newExpression = new LiteralExpression(relevantFeature, Constants.TYPE_LEVEL_LENGTH);
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void replaceStringAggregateFunctionInExpressionConstraint(final ExpressionConstraint parentExpression,
                                                                      final AggregateFunctionExpression aggregateFunctionExpression) {
        this.addAttribute(aggregateFunctionExpression);
        Feature relevantFeature = rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName());
        final Expression newExpression = new LiteralExpression(relevantFeature, Constants.TYPE_LEVEL_LENGTH);
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
            Constants.TYPE_LEVEL_LENGTH,
            new Attribute<Long>(
                Constants.TYPE_LEVEL_LENGTH,
                this.computeStringLength(
                    this.rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getRootFeatureName()))
            )
        );
        this.featuresToBeUpdated.put(aggregateFunctionExpression.getRootFeatureName(), currentAttributes);
    }

    private Long computeStringLength(final Feature feature) {
        if (feature.getAttributes().containsKey("type_level_value")) {
            return (long) feature.getAttributes().get("type_level_value").getValue().toString().length();
        }

        return 0L;
    }
}
