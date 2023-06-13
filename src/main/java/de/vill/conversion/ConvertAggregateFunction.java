package de.vill.conversion;

import de.vill.model.*;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.*;

import java.util.*;

public class ConvertAggregateFunction implements IConversionStrategy {

    private FeatureModel rootFeatureModel;

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.AGGREGATE_FUNCTION));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Arrays.asList(LanguageLevel.ARITHMETIC_LEVEL));
    }

    @Override
    public void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel) {
        this.rootFeatureModel = rootFeatureModel;
        featureModel.getOwnConstraints().forEach(x -> searchAggregateFunctionInConstraint(x));
        traverseFeatures(featureModel.getRootFeature());
    }

    private void searchAggregateFunctionInConstraint(Constraint constraint) {
        if (constraint instanceof ExpressionConstraint) {
            for (Expression expression : ((ExpressionConstraint) constraint).getExpressionSubParts()) {
                if (expression instanceof AggregateFunctionExpression) {
                    replaceAggregateFunctionInExpressionConstraint((ExpressionConstraint) constraint, (AggregateFunctionExpression) expression);
                } else {
                    searchAggregateFunctionInExpression(expression);
                }
            }
        } else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()) {
                searchAggregateFunctionInConstraint(subConstraint);
            }
        }
    }

    private void searchAggregateFunctionInExpression(Expression expression) {
        for (Expression subExpression : expression.getExpressionSubParts()) {
            if (subExpression instanceof AggregateFunctionExpression) {
                replaceAggregateFunctionInExpression(expression, (AggregateFunctionExpression) subExpression);
            } else {
                searchAggregateFunctionInExpression(subExpression);
            }
        }
    }

    private void replaceAggregateFunctionInExpression(Expression parentExpression, AggregateFunctionExpression aggregateFunctionExpression) {
        Feature rootFeature;
        Expression aggregateFunctionReplacement;
        if (aggregateFunctionExpression.getRootFeature() == null) {
            rootFeature = rootFeatureModel.getRootFeature();
        } else {
            rootFeature = aggregateFunctionExpression.getRootFeature();
        }
        List<Feature> attributes = collectAttributes(rootFeature, aggregateFunctionExpression.getAttributeName());
        Expression newExpression = null;
        if (aggregateFunctionExpression instanceof SumAggregateFunctionExpression) {
            newExpression = getSum(attributes, aggregateFunctionExpression.getAttributeName());
        } else if (aggregateFunctionExpression instanceof AvgAggregateFunctionExpression) {
            newExpression = getAvg(attributes, aggregateFunctionExpression.getAttributeName());
        }
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void replaceAggregateFunctionInExpressionConstraint(ExpressionConstraint parentExpression, AggregateFunctionExpression aggregateFunctionExpression) {
        Feature rootFeature;
        Expression aggregateFunctionReplacement;
        if (aggregateFunctionExpression.getRootFeature() == null) {
            rootFeature = rootFeatureModel.getRootFeature();
        } else {
            rootFeature = aggregateFunctionExpression.getRootFeature();
        }
        List<Feature> attributes = collectAttributes(rootFeature, aggregateFunctionExpression.getAttributeName());
        Expression newExpression = null;
        if (aggregateFunctionExpression instanceof SumAggregateFunctionExpression) {
            newExpression = getSum(attributes, aggregateFunctionExpression.getAttributeName());
        } else if (aggregateFunctionExpression instanceof AvgAggregateFunctionExpression) {
            newExpression = getAvg(attributes, aggregateFunctionExpression.getAttributeName());
        }
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private Expression getSum(List<Feature> relevantFeatures, String attributeName) {
        if (relevantFeatures.size() == 0) {
            return new NumberExpression(0);
        } else if (relevantFeatures.size() == 1) {
            LiteralExpression literalExpression = new LiteralExpression(relevantFeatures.get(0), attributeName);
            relevantFeatures.remove(0);

            return literalExpression;
        } else {
            LiteralExpression literalExpression = new LiteralExpression(relevantFeatures.get(0), attributeName);
            relevantFeatures.remove(0);

            return new AddExpression(literalExpression, getSum(relevantFeatures, attributeName));
        }
    }

    private Expression getAvg(List<Feature> relevantFeatures, String attributeName) {
        if (relevantFeatures.size() == 0) {
            return new NumberExpression(0);
        } else {
            for (Feature feature : relevantFeatures) {
                feature.getAttributes().put("avg_counter__", new Attribute<Long>("avg_counter__", 1L));
            }
            Expression n = getSum(new LinkedList<>(relevantFeatures), "avg_counter__");
            Expression sum = getSum(relevantFeatures, attributeName);
            Expression avg = new ParenthesisExpression(new DivExpression(new ParenthesisExpression(sum), new ParenthesisExpression(n)));

            return avg;
        }
    }


    private List<Feature> collectAttributes(Feature feature, String attributeName) {
        List<Feature> result = new LinkedList<>();
        if (feature.getAttributes().containsKey(attributeName)) {
            if (feature.getAttributes().get(attributeName).getValue() instanceof Double || feature.getAttributes().get(attributeName).getValue() instanceof Integer || feature.getAttributes().get(attributeName).getValue() instanceof Long) {
                result.add(feature);
            }
        }
        for (Group group : feature.getChildren()) {
            for (Feature childFeature : group.getFeatures()) {
                result.addAll(collectAttributes(childFeature, attributeName));
            }
        }
        return result;
    }

    private void traverseFeatures(Feature feature) {
        if (!feature.isSubmodelRoot()) {
            removeAggregateFunctionFromAttributes(feature);
            for (Group group : feature.getChildren()) {
                for (Feature subFeature : group.getFeatures()) {
                    traverseFeatures(subFeature);
                }
            }
        }
    }

    private void removeAggregateFunctionFromAttributes(Feature feature) {
        Attribute attributeConstraint = feature.getAttributes().get("constraint");
        Attribute attributeConstraintList = feature.getAttributes().get("constraints");
        if (attributeConstraint != null) {
            if (attributeConstraint.getValue() instanceof Constraint) {
                searchAggregateFunctionInConstraint((Constraint) attributeConstraint.getValue());
            }
        }
        if (attributeConstraintList != null && attributeConstraintList.getValue() instanceof List<?>) {
            List<Object> newConstraintList = new LinkedList<>();
            for (Object constraint : (List<?>) attributeConstraintList.getValue()) {
                if (constraint instanceof Constraint) {
                    searchAggregateFunctionInConstraint((Constraint) constraint);
                }
            }
        }
    }

}
