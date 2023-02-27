package de.vill.conversion;

import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConvertTypeLevel implements IConversionStrategy {

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.SMT_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        rootFeatureModel.getOwnConstraints().forEach(this::convertFeaturesInExpressionConstraint);
        this.traverseFeatures(featureModel.getRootFeature());
    }

    private void convertFeaturesInExpressionConstraint(Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (Expression expression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                replaceFeatureInExpression(expression);
            }
        } else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()) {
                convertFeaturesInExpressionConstraint(subConstraint);
            }
        }
    }

    private void replaceFeatureInExpression(Expression expression) {
        if (expression instanceof LiteralExpression) {
            if (((LiteralExpression) expression).getAttributeName() == null) {
                expression.replaceExpressionSubPart(expression, new LiteralExpression(
                    ((LiteralExpression) expression).getContent() + ".type_level_value")
                );
            }
        }
        for (Expression expr : expression.getExpressionSubParts()) {
            replaceFeatureInExpression(expr);
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
