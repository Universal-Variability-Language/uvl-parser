package de.vill.main;

import de.vill.UVLBaseListener;
import de.vill.UVLParser;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.OrGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class UVLListener extends UVLBaseListener {
    private FeatureModel featureModel = new FeatureModel();
    private Stack<List<Feature>> featureStack = new Stack<>();
    private Stack<List<Group>> groupStack = new Stack<>();


    @Override public void enterRootFeature(UVLParser.RootFeatureContext ctx) {
        groupStack.push(new LinkedList<>());
    }

    @Override public void exitRootFeature(UVLParser.RootFeatureContext ctx) {
        Feature feature = new Feature(ctx.FEATURENAME().getText());
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

    @Override public void enterFeature(UVLParser.FeatureContext ctx) {
        groupStack.push(new LinkedList<>());
    }

    @Override public void exitFeature(UVLParser.FeatureContext ctx) {
        Feature feature = new Feature(ctx.FEATURENAME().getText());
        List<Group> groupList = groupStack.pop();
        for(Group group : groupList){
            feature.addChildren(group);
        }
        featureStack.peek().add(feature);
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
