package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.FeatureType;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;
import de.vill.util.Constants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConvertTypeLevel implements IConversionStrategy {
    FeatureModel rootFeatureModel;

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.ARITHMETIC_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        this.rootFeatureModel = rootFeatureModel;
        this.rootFeatureModel.getOwnConstraints().forEach(this::convertFeaturesInExpressionConstraint);
        this.traverseFeatures(featureModel.getRootFeature());
    }

    private void convertFeaturesInExpressionConstraint(final Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (final Expression expression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                this.replaceFeatureInExpression(expression);
            }
        } else {
            for (final Constraint subConstraint : constraint.getConstraintSubParts()) {
                this.convertFeaturesInExpressionConstraint(subConstraint);
            }
        }
    }

    private void replaceFeatureInExpression(final Expression expression) {
        if (expression instanceof LiteralExpression) {
            if (((LiteralExpression) expression).getAttributeName() == null) {
                Feature relevantFeature = rootFeatureModel.getFeatureMap().get(((LiteralExpression) expression).getFeatureName());
                expression.replaceExpressionSubPart(expression, new LiteralExpression(relevantFeature, Constants.TYPE_LEVEL_VALUE)
                );
            }
        }
        for (final Expression expr : expression.getExpressionSubParts()) {
            this.replaceFeatureInExpression(expr);
        }
    }

    private void traverseFeatures(final Feature feature) {
        if (feature.getFeatureType() != null) {
            feature.getAttributes().put(
                "feature_type",
                new Attribute<>("feature_type", feature.getFeatureType().getName())
            );
            feature.setFeatureType(null);
        }

        for (final Group group : feature.getChildren()) {
            for (final Feature subFeature : group.getFeatures()) {
                this.traverseFeatures(subFeature);
            }
        }
    }
}
