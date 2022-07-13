package de.vill.main;

import de.vill.UVLBaseListener;
import de.vill.UVLParser;
import de.vill.exception.ParseError;
import de.vill.model.*;

import java.util.*;

public class UVLListener extends UVLBaseListener {
    private FeatureModel featureModel = new FeatureModel();
    private Stack<Feature> featureStack = new Stack<>();
    private Stack<Group> groupStack = new Stack<>();

    private Stack<Constraint> constraintStack = new Stack<>();

    private Stack<Expression> expressionStack = new Stack<>();

    private Stack<Map<String,Attribute>> attributeStack = new Stack<>();

    private boolean featureCardinality = false;

    @Override public void exitNamespace(UVLParser.NamespaceContext ctx) {
        featureModel.setNamespace(ctx.REFERENCE().getText());
    }

    @Override public void exitImportLine(UVLParser.ImportLineContext ctx) {
        Import importLine;
        if(ctx.alias != null){
            importLine = new Import(ctx.ns.getText(), ctx.alias.getText());
        }else{
            importLine = new Import(ctx.ns.getText(), null);
        }
        featureModel.getImports().add(importLine);
    }

    @Override public void enterFeatures(UVLParser.FeaturesContext ctx) {
        groupStack.push(new MandatoryGroup());
    }

    @Override public void exitFeatures(UVLParser.FeaturesContext ctx) {
        Group group = groupStack.pop();
        Feature feature = group.getFeatures().get(0);
        featureModel.setRootFeature(feature);
    }

    @Override public void enterGroupSpec(UVLParser.GroupSpecContext ctx) {

    }

    @Override public void enterOrGroup(UVLParser.OrGroupContext ctx) {
        Group group = new OrGroup();
        Feature feature = featureStack.peek();
        feature.addChildren(group);
        groupStack.push(group);
    }

    @Override public void exitOrGroup(UVLParser.OrGroupContext ctx) {
        groupStack.pop();
    }

    @Override public void enterAlternativeGroup(UVLParser.AlternativeGroupContext ctx) {
        Group group = new AlternativeGroup();
        Feature feature = featureStack.peek();
        feature.addChildren(group);
        groupStack.push(group);
    }

    @Override public void exitAlternativeGroup(UVLParser.AlternativeGroupContext ctx) {
        groupStack.pop();
    }

    @Override public void enterOptionalGroup(UVLParser.OptionalGroupContext ctx) {
        Group group = new OptionalGroup();
        Feature feature = featureStack.peek();
        feature.addChildren(group);
        groupStack.push(group);
    }

    @Override public void exitOptionalGroup(UVLParser.OptionalGroupContext ctx) {
        groupStack.pop();
    }

    @Override public void enterMandatoryGroup(UVLParser.MandatoryGroupContext ctx) {
        Group group = new MandatoryGroup();
        Feature feature = featureStack.peek();
        feature.addChildren(group);
        groupStack.push(group);
    }

    @Override public void exitMandatoryGroup(UVLParser.MandatoryGroupContext ctx) {
        groupStack.pop();
    }

    @Override public void enterCardinalityGroup(UVLParser.CardinalityGroupContext ctx) {
        CardinalityGroup group = new CardinalityGroup();
        group.setLowerBound(ctx.lowerBound.getText());
        if(ctx.upperBound != null) {
            group.setUpperBound(ctx.upperBound.getText());
        } else {
            group.setUpperBound(ctx.lowerBound.getText());
        }

        Feature feature = featureStack.peek();
        feature.addChildren(group);
        groupStack.push(group);

        featureModel.getUsedLanguageLevels().add(LanguageLevel.GROUP_CARDINALITY);
    }

    @Override public void exitCardinalityGroup(UVLParser.CardinalityGroupContext ctx) {
        groupStack.pop();
    }

    @Override public void enterFeature(UVLParser.FeatureContext ctx) {
        String featureReference = ctx.REFERENCE().getText();

        Feature feature = new Feature(featureReference);

        if(featureReference.contains(".")){
            int lastDotIndex = featureReference.lastIndexOf(".");
            String subModelName = featureReference.substring(0, lastDotIndex);
            String featureName = featureReference.substring(lastDotIndex + 1, featureReference.length());
            for(Import importLine : featureModel.getImports()){
                if(importLine.getAlias().equals(subModelName)){
                    feature.setImported(true);
                    feature.setRelatedImport(importLine);
                    break;
                }
            }
        }

        featureStack.push(feature);
        groupStack.peek().addFeature(feature);
        featureModel.getFeatureMap().put(feature.getNAME(), feature);
    }

    @Override public void exitFeature(UVLParser.FeatureContext ctx) {
       featureStack.pop();
    }

    @Override public void exitFeatureCardinality(UVLParser.FeatureCardinalityContext ctx) {
        Feature feature = featureStack.peek();
        feature.setLowerBound(ctx.lowerBound.getText());
        if(ctx.upperBound != null) {
            feature.setUpperBound(ctx.upperBound.getText());
        }else {
            feature.setUpperBound(ctx.lowerBound.getText());
        }

        featureModel.getUsedLanguageLevels().add(LanguageLevel.SMT_LEVEL);
        featureModel.getUsedLanguageLevels().add(LanguageLevel.FEATURE_CARDINALITY);
    }

    @Override public void enterAttributes(UVLParser.AttributesContext ctx) {
        attributeStack.push(new HashMap<>());
    }

    @Override public void exitAttributes(UVLParser.AttributesContext ctx) {
        if(attributeStack.size() == 1){
            Feature feature = featureStack.peek();
            feature.setAttributes(attributeStack.pop());
        }
    }


    @Override public void exitAttribute(UVLParser.AttributeContext ctx) {
        if (ctx.value() == null){
            Attribute<Boolean> attribute = new Attribute(ctx.key().getText());
            attribute.setValue(true);
            attributeStack.peek().put(ctx.key().getText(), attribute);
        }else if(ctx.value().BOOLEAN() != null){
            Attribute<Boolean> attribute = new Attribute(ctx.key().getText());
            attribute.setValue(Boolean.parseBoolean(ctx.value().getText()));
            attributeStack.peek().put(ctx.key().getText(), attribute);
        }else if(ctx.value().INTEGER() != null){
            Attribute<Integer> attribute = new Attribute(ctx.key().getText());
            attribute.setValue(Integer.parseInt(ctx.value().getText()));
            attributeStack.peek().put(ctx.key().getText(), attribute);
        }else if(ctx.value().STRING() != null) {
            Attribute<String> attribute = new Attribute(ctx.key().getText());
            attribute.setValue(ctx.value().getText());
            attributeStack.peek().put(ctx.key().getText(), attribute);
        }else if(ctx.value().vector() != null) {
            Attribute<List<String>> attribute = new Attribute(ctx.key().getText());
            String vectorString = ctx.value().getText();
            vectorString = vectorString.substring(1, vectorString.length() - 1);
            attribute.setValue(Arrays.asList(vectorString.split(",")));
            attributeStack.peek().put(ctx.key().getText(), attribute);
        }else if(ctx.value().attributes() != null){
            var attributes = attributeStack.pop();
            Attribute<Map<String, Attribute>> attribute = new Attribute<>(ctx.key().getText());
            attribute.setValue(attributes);
            attributeStack.peek().put(ctx.key().getText(), attribute);
        }else {
            throw new ParseError(0,0,ctx.value().getText() + " is no value of any supported attribute type!", null);
        }
    }

    @Override public void exitLiteralConstraint(UVLParser.LiteralConstraintContext ctx) {
        String featureReference = ctx.REFERENCE().getText();

        var constraint = new LiteralConstraint(featureReference);

        if(featureReference.contains(".")){
            int lastDotIndex = featureReference.lastIndexOf(".");
            String subModelName = featureReference.substring(0, lastDotIndex);
            String featureName = featureReference.substring(lastDotIndex + 1, featureReference.length());
            for(Import importLine : featureModel.getImports()){
                if(importLine.getAlias().equals(subModelName)){
                    constraint.setRelatedImport(importLine);
                    break;
                }
            }
        }
        featureModel.getLiteralConstraints().add(constraint);
        constraintStack.push(constraint);
    }

    @Override public void exitParenthesisConstraint(UVLParser.ParenthesisConstraintContext ctx) {
        Constraint constraint = new ParenthesisConstraint(constraintStack.pop());
        constraintStack.push(constraint);
    }

    @Override public void exitNotConstraint(UVLParser.NotConstraintContext ctx) {
        Constraint constraint = new NotConstraint(constraintStack.pop());
        constraintStack.push(constraint);
    }

    @Override public void exitAndConstraint(UVLParser.AndConstraintContext ctx) {
        Constraint rightConstraint = constraintStack.pop();
        Constraint leftConstraint = constraintStack.pop();
        Constraint constraint = new AndConstraint(leftConstraint, rightConstraint);
        constraintStack.push(constraint);
    }

    @Override public void exitOrConstraint(UVLParser.OrConstraintContext ctx) {
        Constraint rightConstraint = constraintStack.pop();
        Constraint leftConstraint = constraintStack.pop();
        Constraint constraint = new OrConstraint(leftConstraint, rightConstraint);
        constraintStack.push(constraint);
    }

    @Override public void exitImplicationConstraint(UVLParser.ImplicationConstraintContext ctx) {
        Constraint rightConstraint = constraintStack.pop();
        Constraint leftConstraint = constraintStack.pop();
        Constraint constraint = new ImplicationConstraint(leftConstraint, rightConstraint);
        constraintStack.push(constraint);
    }

    @Override public void exitEquivalenceConstraint(UVLParser.EquivalenceConstraintContext ctx) {
        Constraint rightConstraint = constraintStack.pop();
        Constraint leftConstraint = constraintStack.pop();
        Constraint constraint = new EquivalenceConstraint(leftConstraint, rightConstraint);
        constraintStack.push(constraint);
    }

    @Override public void exitEqualEquation(UVLParser.EqualEquationContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Constraint constraint = new EqualEquationConstraint(left, right);
        constraintStack.push(constraint);
    }

    @Override public void exitLowerEquation(UVLParser.LowerEquationContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Constraint constraint = new LowerEquationConstraint(left, right);
        constraintStack.push(constraint);
    }

    @Override public void exitGreaterEquation(UVLParser.GreaterEquationContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Constraint constraint = new GreaterEquationConstraint(left, right);
        constraintStack.push(constraint);
    }

    @Override public void exitIntegerLiteralExpression(UVLParser.IntegerLiteralExpressionContext ctx) {
        Expression expression = new NumberExpression(Integer.parseInt(ctx.INTEGER().getText()));
        expressionStack.push(expression);
    }

    @Override public void exitAttributeLiteralExpression(UVLParser.AttributeLiteralExpressionContext ctx) {
        LiteralExpression expression = new LiteralExpression(ctx.REFERENCE().getText());
        expressionStack.push(expression);
        featureModel.getLiteralExpressions().add(expression);
    }

    @Override public void exitAddExpression(UVLParser.AddExpressionContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Expression expression = new AddExpression(left, right);
        expressionStack.push(expression);
    }

    @Override public void exitSubExpression(UVLParser.SubExpressionContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Expression expression = new SubExpression(left, right);
        expressionStack.push(expression);
    }

    @Override public void exitMulExpression(UVLParser.MulExpressionContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Expression expression = new MulExpression(left, right);
        expressionStack.push(expression);
    }

    @Override public void exitDivExpresssion(UVLParser.DivExpresssionContext ctx) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Expression expression = new DivExpression(left, right);
        expressionStack.push(expression);
    }

    @Override public void exitMinAggregateFunction(UVLParser.MinAggregateFunctionContext ctx) {
        Expression expression;
        if(ctx.REFERENCE().size() > 1) {
            expression = new MinAggregateFunctionExpression(ctx.REFERENCE().get(0).getText(), ctx.REFERENCE().get(1).getText());
        }else {
            expression = new MinAggregateFunctionExpression(ctx.REFERENCE().get(0).getText());
        }
        expressionStack.push(expression);
    }

    @Override public void exitMaxAggregateFunction(UVLParser.MaxAggregateFunctionContext ctx) {
        Expression expression;
        if(ctx.REFERENCE().size() > 1) {
            expression = new MaxAggregateFunctionExpression(ctx.REFERENCE().get(0).getText(), ctx.REFERENCE().get(1).getText());
        }else {
            expression = new MaxAggregateFunctionExpression(ctx.REFERENCE().get(0).getText());
        }
        expressionStack.push(expression);
    }

    @Override public void exitSumAggregateFunction(UVLParser.SumAggregateFunctionContext ctx) {
        Expression expression;
        if(ctx.REFERENCE().size() > 1) {
            expression = new SumAggregateFunctionExpression(ctx.REFERENCE().get(0).getText(), ctx.REFERENCE().get(1).getText());
        }else {
            expression = new SumAggregateFunctionExpression(ctx.REFERENCE().get(0).getText());
        }
        expressionStack.push(expression);
    }

    @Override public void exitAvgAggregateFunction(UVLParser.AvgAggregateFunctionContext ctx) {
        Expression expression;
        if(ctx.REFERENCE().size() > 1) {
            expression = new AvgAggregateFunctionExpression(ctx.REFERENCE().get(0).getText(), ctx.REFERENCE().get(1).getText());
        }else {
            expression = new AvgAggregateFunctionExpression(ctx.REFERENCE().get(0).getText());
        }
        expressionStack.push(expression);
    }

    @Override public void enterAggregateFunctionExpression(UVLParser.AggregateFunctionExpressionContext ctx) {
        featureModel.getUsedLanguageLevels().add(LanguageLevel.SMT_LEVEL);
        featureModel.getUsedLanguageLevels().add(LanguageLevel.AGGREGATE_FUNCTION);
    }

    @Override public void enterEquationConstraint(UVLParser.EquationConstraintContext ctx) {
        featureModel.getUsedLanguageLevels().add(LanguageLevel.SMT_LEVEL);
    }

    @Override public void exitConstraintLine(UVLParser.ConstraintLineContext ctx) {
        featureModel.getConstraints().add(constraintStack.pop());
    }

    public FeatureModel getFeatureModel() {
        return featureModel;
    }

/*
    @Override public void exitFeatureModel(UVLParser.FeatureModelContext ctx) {
        System.out.println("featuremodel");
    }

    @Override public void exitFeatures(UVLParser.FeaturesContext ctx) {
        System.out.println("features");
    }

    @Override public void exitRootFeature(UVLParser.RootFeatureContext ctx) {
        System.out.println("rootFeature");
    }

    @Override public void exitGroup(UVLParser.GroupContext ctx) {
        System.out.println("group");
    }

    @Override public void exitFeature(UVLParser.FeatureContext ctx) {
        System.out.println("feature");
    }

 */


}
