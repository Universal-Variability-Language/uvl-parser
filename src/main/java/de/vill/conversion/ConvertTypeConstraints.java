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

public class ConvertTypeConstraints implements IConversionStrategy {
    private final Map<String, Map<String, Attribute>> featuresToBeUpdated = new HashMap<>();
    private FeatureModel rootFeatureModel;

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_CONSTRAINTS));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        this.rootFeatureModel = rootFeatureModel;
        rootFeatureModel.getOwnConstraints().forEach(this::convertAggregateFunctionInExpressionConstraint);
        traverseFeatures(rootFeatureModel.getRootFeature());
    }

    private void convertAggregateFunctionInExpressionConstraint(Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (Expression expression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expression instanceof LengthAggregateFunctionExpression) {
                    replaceAggregateFunctionInExpressionConstraint((ExpressionConstraint) constraint, (AggregateFunctionExpression) expression);
                } else {
                    convertAggregateFunctionInExpression(expression);
                }
            }
        } else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()) {
                convertAggregateFunctionInExpressionConstraint(subConstraint);
            }
        }
    }

    private void convertAggregateFunctionInExpression(Expression expression) {
        for (Expression subExpression : expression.getExpressionSubParts()) {
            if (subExpression instanceof LengthAggregateFunctionExpression) {
                replaceAggregateFunctionInExpression(expression, (LengthAggregateFunctionExpression) subExpression);
            } else {
                convertAggregateFunctionInExpression(subExpression);
            }
        }
    }

    private void replaceAggregateFunctionInExpression(Expression parentExpression, AggregateFunctionExpression aggregateFunctionExpression) {
        Map<String, Attribute> currentAttributes =
            rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getAttributeName()).getAttributes();
        currentAttributes.put(
            "type_level_value_length",
            new Attribute<>(
                "type_level_value_length",
                this.computeStringLength(
                    rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getAttributeName())).toString()
            )
        );
        this.featuresToBeUpdated.put(aggregateFunctionExpression.getAttributeName(), currentAttributes);

        Expression newExpression = new LiteralExpression(aggregateFunctionExpression.getAttributeName() + ".type_level_value_length");
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void replaceAggregateFunctionInExpressionConstraint(ExpressionConstraint parentExpression,
                                                                AggregateFunctionExpression aggregateFunctionExpression) {
        Map<String, Attribute> currentAttributes =
            rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getAttributeName()).getAttributes();
        currentAttributes.put(
            "type_level_value_length",
            new Attribute<>(
                "type_level_value_length",
                this.computeStringLength(
                    rootFeatureModel.getFeatureMap().get(aggregateFunctionExpression.getAttributeName())).toString()
            )
        );
        this.featuresToBeUpdated.put(aggregateFunctionExpression.getAttributeName(), currentAttributes);

        Expression newExpression = new LiteralExpression(aggregateFunctionExpression.getAttributeName() + ".type_level_value_length");
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

    private Integer computeStringLength(final Feature feature) {
        if (feature.getAttributes().containsKey("type_level_value")) {
            return feature.getAttributes().get("type_level_value").getValue().toString().length();
        }

        return 0;
    }
}
