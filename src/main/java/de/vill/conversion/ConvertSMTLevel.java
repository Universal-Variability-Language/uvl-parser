package de.vill.conversion;

import com.google.common.collect.Sets;
import de.vill.model.*;
import de.vill.model.constraint.*;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;

import java.util.*;

public class ConvertSMTLevel implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.ARITHMETIC_LEVEL));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Arrays.asList(LanguageLevel.BOOLEAN_LEVEL));
    }

    @Override
    public void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel) {
        List<Constraint> constraints = featureModel.getFeatureConstraints();
        constraints.addAll(featureModel.getOwnConstraints());
        constraints.stream().forEach(x -> replaceEquationInConstraint(x));
        List<Constraint> replacements = new LinkedList<>();
        for (Constraint constraint : featureModel.getOwnConstraints()) {
            if (constraint instanceof ExpressionConstraint) {
                Constraint equationReplacement = convertEquationToConstraint((ExpressionConstraint) constraint);
                replacements.add(equationReplacement);
            }
        }
        featureModel.getOwnConstraints().removeIf(x -> x instanceof ExpressionConstraint);
        featureModel.getOwnConstraints().addAll(replacements);
        traverseFeatures(featureModel.getRootFeature());
    }

    private void replaceEquationInConstraint(Constraint constraint) {
        for (Constraint subConstraint : constraint.getConstraintSubParts()) {
            if (subConstraint instanceof ExpressionConstraint) {
                Constraint equationReplacement = convertEquationToConstraint((ExpressionConstraint) subConstraint);
                constraint.replaceConstraintSubPart(subConstraint, equationReplacement);
            }
        }
    }

    private Constraint convertEquationToConstraint(ExpressionConstraint equation) {
        Set<Feature> featuresInEquation = getFeaturesInEquation(equation);
        Set<Set<Feature>> featureCombinations = getFeatureCombinations(featuresInEquation);
        Set<Constraint> disjunction = new HashSet<>();
        for (Set<Feature> configuration : featureCombinations) {
            boolean result = equation.evaluate(configuration);
            if (result) {
                disjunction.add(createConjunction(configuration, new HashSet<>(featuresInEquation)));
            }
        }
        return new ParenthesisConstraint(createDisjunction(disjunction));
    }

    private Set<Feature> getFeaturesInEquation(ExpressionConstraint equation) {
        Set<Feature> featuresInEquation = new HashSet<>();
        for (Expression expression : equation.getExpressionSubParts()) {
            featuresInEquation.addAll(getFeaturesInExpression(expression));
        }
        return featuresInEquation;
    }

    private Set<Feature> getFeaturesInExpression(Expression expression) {
        Set<Feature> featuresInEquation = new HashSet<>();
        if (expression instanceof LiteralExpression) {
            featuresInEquation.add(((LiteralExpression) expression).getFeature());
        } else {
            for (Expression subExpression : expression.getExpressionSubParts()) {
                featuresInEquation.addAll(getFeaturesInExpression(subExpression));
            }
        }
        return featuresInEquation;
    }

    private Set<Set<Feature>> getFeatureCombinations(Set<Feature> features) {
        Set<Set<Feature>> featureCombinations = new HashSet<>();
        for (int i = 0; i <= features.size(); i++) {
            featureCombinations.addAll(Sets.combinations(features, i));
        }
        return featureCombinations;
    }

    private Constraint createConjunction(Set<Feature> selectedFeatures, Set<Feature> allFeatures) {
        Constraint constraint;
        Feature feature = null;
        if (allFeatures.size() >= 1) {
            feature = allFeatures.iterator().next();
            allFeatures.remove(feature);
        }
        Constraint literalConstraint = new LiteralConstraint(feature.getFeatureName());
        ((LiteralConstraint) literalConstraint).setFeature(feature);
        if (!selectedFeatures.contains(feature)) {
            literalConstraint = new NotConstraint(literalConstraint);
        }
        if (allFeatures.size() == 0) {
            constraint = literalConstraint;
        } else {
            constraint = new AndConstraint(literalConstraint, createConjunction(selectedFeatures, allFeatures));
        }

        return constraint;
    }

    private Constraint createDisjunction(Set<Constraint> constraints) {
        Constraint orConstraint;
        if (constraints.size() == 1) {
            Constraint constraint = constraints.iterator().next();
            constraints.remove(constraint);
            orConstraint = constraint;
        } else {
            Constraint constraint = constraints.iterator().next();
            constraints.remove(constraint);
            orConstraint = new OrConstraint(constraint, createDisjunction(constraints));
        }

        return orConstraint;
    }

    private void removeEquationFromAttributes(Feature feature) {
        Attribute attributeConstraint = feature.getAttributes().get("constraint");
        Attribute attributeConstraintList = feature.getAttributes().get("constraints");
        if (attributeConstraint != null) {
            if (attributeConstraint.getValue() instanceof ExpressionConstraint) {
                Constraint equationReplacement = convertEquationToConstraint((ExpressionConstraint) attributeConstraint.getValue());
                feature.getAttributes().put("constraint", new Attribute("constraint", equationReplacement));
            }
        }
        if (attributeConstraintList != null && attributeConstraintList.getValue() instanceof List<?>) {
            List<Object> newConstraintList = new LinkedList<>();
            for (Object constraint : (List<?>) attributeConstraintList.getValue()) {
                if (constraint instanceof ExpressionConstraint) {
                    Constraint equationReplacement = convertEquationToConstraint((ExpressionConstraint) constraint);
                    newConstraintList.add(equationReplacement);
                } else {
                    newConstraintList.add(constraint);
                }
            }
            feature.getAttributes().put("constraints", new Attribute("constraints", newConstraintList));
        }
    }

    private void traverseFeatures(Feature feature) {
        removeEquationFromAttributes(feature);
        for (Group group : feature.getChildren()) {
            for (Feature subFeature : group.getFeatures()) {
                traverseFeatures(subFeature);
            }
        }
    }


}
