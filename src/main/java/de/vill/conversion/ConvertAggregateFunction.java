package de.vill.conversion;

import de.vill.model.*;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ExpressionConstraint;
import de.vill.model.expression.*;

import java.util.*;

public class ConvertAggregateFunction implements IConversionStrategy{

    private FeatureModel rootFeatureModel;

    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.AGGREGATE_FUNCTION));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Arrays.asList(LanguageLevel.SMT_LEVEL));
    }

    @Override
    public void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel) {
        this.rootFeatureModel = rootFeatureModel;
        featureModel.getOwnConstraints().forEach(x -> searchAggregateFunctionInConstraint(x));
    }

    private void searchAggregateFunctionInConstraint(Constraint constraint){
        if(constraint instanceof ExpressionConstraint){
            for(Expression expression : ((ExpressionConstraint)constraint).getExpressionSubParts()){
                if(expression instanceof AggregateFunctionExpression){
                    replaceAggregateFunctionInExpressionConstraint((ExpressionConstraint) constraint, (AggregateFunctionExpression) expression);
                }else {
                    searchAggregateFunctionInExpression(expression);
                }
            }
        }else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()){
                searchAggregateFunctionInConstraint(subConstraint);
            }
        }
    }

    private void searchAggregateFunctionInExpression(Expression expression){
        for(Expression subExpression : expression.getExpressionSubParts()){
            if (subExpression instanceof AggregateFunctionExpression){
                replaceAggregateFunctionInExpression(expression, (AggregateFunctionExpression) subExpression);
            }else {
                searchAggregateFunctionInExpression(subExpression);
            }
        }
    }


/*
    private boolean constraintContainsAggregateFunction(Constraint constraint){
        if(constraint instanceof ExpressionConstraint){
            for (Expression subExpression : ((ExpressionConstraint) constraint).getExpressionSubParts()){
                if(expressionContainsAggregateFunction(subExpression)){
                    return true;
                }
            }
        }else {
            for (Constraint subConstraint : constraint.getConstraintSubParts()){
                if(constraintContainsAggregateFunction(subConstraint)){
                    return true;
                }
            }
        }

        return false;
    }

    //TODO
    private AggregateFunctionExpression expressionContainsAggregateFunction(Expression expression){
        if(expression instanceof AggregateFunctionExpression){
            return (AggregateFunctionExpression) expression;
        }else {
            for(Expression subExpression : expression.getExpressionSubParts()){
                AggregateFunctionExpression aggregateFunctionExpression = expressionContainsAggregateFunction(subExpression);
                if(aggregateFunctionExpression != null){
                    replaceAggregateFunctionInConstraint(subExpression, aggregateFunctionExpression);
                }
            }
        }
        return null;
    }

 */

    private void replaceAggregateFunctionInExpression(Expression parentExpression, AggregateFunctionExpression aggregateFunctionExpression){
        Feature rootFeature;
        Expression aggregateFunctionReplacement;
        if(aggregateFunctionExpression.getRootFeature() == null){
            rootFeature = rootFeatureModel.getRootFeature();
        }else {
            rootFeature = aggregateFunctionExpression.getRootFeature();
        }
        List<Feature> attributes = collectAttributes(rootFeature, aggregateFunctionExpression.getAttributeName());
        Expression newExpression = null;
        if(aggregateFunctionExpression instanceof SumAggregateFunctionExpression){
            newExpression = getSum(attributes, aggregateFunctionExpression.getAttributeName());
        } else if (aggregateFunctionExpression instanceof AvgAggregateFunctionExpression) {
            newExpression = getAvg(attributes, aggregateFunctionExpression.getAttributeName());
        }
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private void replaceAggregateFunctionInExpressionConstraint(ExpressionConstraint parentExpression, AggregateFunctionExpression aggregateFunctionExpression){
        Feature rootFeature;
        Expression aggregateFunctionReplacement;
        if(aggregateFunctionExpression.getRootFeature() == null){
            rootFeature = rootFeatureModel.getRootFeature();
        }else {
            rootFeature = aggregateFunctionExpression.getRootFeature();
        }
        List<Feature> attributes = collectAttributes(rootFeature, aggregateFunctionExpression.getAttributeName());
        Expression newExpression = null;
        if(aggregateFunctionExpression instanceof SumAggregateFunctionExpression){
            newExpression = getSum(attributes, aggregateFunctionExpression.getAttributeName());
        } else if (aggregateFunctionExpression instanceof AvgAggregateFunctionExpression) {
            newExpression = getAvg(attributes, aggregateFunctionExpression.getAttributeName());
        }
        parentExpression.replaceExpressionSubPart(aggregateFunctionExpression, newExpression);
    }

    private Expression getSum(List<Feature> attributes, String attributeName){
        if(attributes.size() == 0){
            return new NumberExpression(0);
        }else if(attributes.size() == 1){
            LiteralExpression literalExpression = new LiteralExpression(attributeName);
            literalExpression.setFeature(attributes.get(0));
            attributes.remove(0);

            return literalExpression;
        }else {
            LiteralExpression literalExpression = new LiteralExpression(attributeName);
            literalExpression.setFeature(attributes.get(0));
            attributes.remove(0);

            return new AddExpression(literalExpression, getSum(attributes, attributeName));
        }
    }

    private Expression getAvg(List<Feature> attributes, String attributeName){
        if(attributes.size() == 0){
            return new NumberExpression(0);
        }else {
            Expression n = new NumberExpression(attributes.size());
            Expression sum = getSum(attributes, attributeName);
            Expression avg = new DivExpression(new ParenthesisExpression(sum), n);

            return avg;
        }
    }


    private List<Feature> collectAttributes(Feature feature, String attributeName){
        List<Feature> result = new LinkedList<>();
        if(feature.getAttributes().containsKey(attributeName)){
            if(feature.getAttributes().get(attributeName).getValue() instanceof Double || feature.getAttributes().get(attributeName).getValue() instanceof Integer) {
                result.add(feature);
            }
        }
        for (Group group : feature.getChildren()){
            for(Feature childFeature : group.getFeatures()){
                result.addAll(collectAttributes(childFeature, attributeName));
            }
        }
        return  result;
    }
}
