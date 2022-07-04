package de.vill.main;

import de.vill.UVLBaseListener;
import de.vill.UVLParser;
import de.vill.model.*;
import org.antlr.v4.runtime.Token;

import java.util.*;

public class UVLListener extends UVLBaseListener {
    private FeatureModel featureModel = new FeatureModel();
    private Stack<List<Feature>> featureStack = new Stack<>();
    private Stack<List<Group>> groupStack = new Stack<>();

    private Stack<Constraint> constraintStack = new Stack<>();

    private Map<String, Object> attribtues = new HashMap<>();

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

    @Override public void enterRootFeature(UVLParser.RootFeatureContext ctx) {
        groupStack.push(new LinkedList<>());
    }

    @Override public void exitRootFeature(UVLParser.RootFeatureContext ctx) {
        Feature feature = new Feature(ctx.REFERENCE().getText());
        List<Group> groupList = groupStack.pop();
        for(Group group : groupList){
            feature.addChildren(group);
        }
        featureModel.setRootFeature(feature);
    }

    @Override public void enterGroupSpec(UVLParser.GroupSpecContext ctx) {
        featureStack.push(new LinkedList<>());
    }

    @Override public void exitOrGroup(UVLParser.OrGroupContext ctx) {
        Group group = new OrGroup();
        List<Feature> featureList = featureStack.pop();
        for(Feature feature : featureList){
            group.addFeature(feature);
        }
        groupStack.peek().add(group);
    }

    @Override public void exitAlternativeGroup(UVLParser.AlternativeGroupContext ctx) {
        Group group = new AlternativeGroup();
        List<Feature> featureList = featureStack.pop();
        for(Feature feature : featureList){
            group.addFeature(feature);
        }
        groupStack.peek().add(group);
    }

    @Override public void exitOptionalGroup(UVLParser.OptionalGroupContext ctx) {
        Group group = new OptionalGroup();
        List<Feature> featureList = featureStack.pop();
        for(Feature feature : featureList){
            group.addFeature(feature);
        }
        groupStack.peek().add(group);
    }

    @Override public void exitMandatoryGroup(UVLParser.MandatoryGroupContext ctx) {
        Group group = new MandatoryGroup();
        List<Feature> featureList = featureStack.pop();
        for(Feature feature : featureList){
            group.addFeature(feature);
        }
        groupStack.peek().add(group);
    }

    @Override public void exitCardinalityGroup(UVLParser.CardinalityGroupContext ctx) {
        CardinalityGroup group = new CardinalityGroup();
        group.setLowerBound(ctx.lowerBound.getText());
        if(ctx.upperBound != null) {
            group.setUpperBound(ctx.upperBound.getText());
        } else {
            group.setUpperBound(ctx.lowerBound.getText());
        }

        List<Feature> featureList = featureStack.pop();
        for(Feature feature : featureList){
            group.addFeature(feature);
        }
        groupStack.peek().add(group);
    }

    @Override public void enterFeature(UVLParser.FeatureContext ctx) {
        groupStack.push(new LinkedList<>());
    }

    @Override public void exitFeature(UVLParser.FeatureContext ctx) {
        Feature feature = new Feature(ctx.REFERENCE().getText());
        List<Group> groupList = groupStack.pop();
        for(Group group : groupList){
            feature.addChildren(group);
        }
        for(Map.Entry<String, Object> entry : attribtues.entrySet()){
            feature.setAttribute(entry.getKey(), entry.getValue());
        }
        attribtues = new HashMap<String, Object>();
        featureStack.peek().add(feature);
        featureModel.getFeatureMap().put(feature.getNAME(), feature);
    }


    @Override public void exitAttribute(UVLParser.AttributeContext ctx) {
        if(ctx.value() != null) {
            attribtues.put(ctx.key().getText(), ctx.value().getText());
        }else {
            attribtues.put(ctx.key().getText(), "");
        }
    }

    @Override public void exitLiteralConstraint(UVLParser.LiteralConstraintContext ctx) {
        Constraint constraint = new LiteralConstraint(ctx.REFERENCE().getText());
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
