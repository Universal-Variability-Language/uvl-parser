package de.vill.conversion;

import de.vill.model.*;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ImplicationConstraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.ParenthesisConstraint;

import java.util.*;

public class ConvertFeatureCardinality implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Arrays.asList(LanguageLevel.FEATURE_CARDINALITY));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Arrays.asList(LanguageLevel.ARITHMETIC_LEVEL));
    }

    @Override
    public void convertFeatureModel(FeatureModel rootFeatureModel, FeatureModel featureModel) {
        traverseFeatures(featureModel.getRootFeature(), featureModel);
    }

    private void traverseFeatures(Feature feature, FeatureModel featureModel) {
        if (!feature.isSubmodelRoot()) {
            if (feature.getLowerBound() != null) {
                List<Feature> subTreeFeatures = new LinkedList<>();
                for (Group group : feature.getChildren()) {
                    subTreeFeatures.addAll(getFeatureFromSubTree(group));
                }
                List<Constraint> constraintsToClone = getConstraintsOnSubTree(featureModel, subTreeFeatures);

                removeFeatureCardinality(feature, featureModel, constraintsToClone);
            }
            for (Group children : feature.getChildren()) {
                for (Feature subFeature : children.getFeatures()) {
                    traverseFeatures(subFeature, featureModel);
                }
            }
        }
    }

    private void removeFeatureCardinality(Feature feature, FeatureModel featureModel, List<Constraint> constraintsToClone) {
        int min = Integer.parseInt(feature.getLowerBound());
        int max = Integer.parseInt(feature.getUpperBound());
        Group newChildren = new Group(Group.GroupType.ALTERNATIVE);

        feature.setLowerBound(null);
        feature.setUpperBound(null);

        for (int i = min; i <= max; i++) {
            Feature newChild = new Feature(feature.getFeatureName() + "-" + i);
            newChild.getAttributes().put("abstract", new Attribute<Boolean>("abstract", true));
            newChildren.getFeatures().add(newChild);
            newChild.setParentGroup(newChildren);
            Group mandatoryGroup = new Group(Group.GroupType.MANDATORY);
            if (i > 0) {
                newChild.getChildren().add(mandatoryGroup);
                mandatoryGroup.setParentFeature(newChild);
            }
            for (int j = 1; j <= i; j++) {
                Feature subTreeClone = feature.clone();
                addPrefixToNamesRecursively(subTreeClone, "-" + i + "-" + j);
                mandatoryGroup.getFeatures().add(subTreeClone);
                subTreeClone.setParentGroup(mandatoryGroup);

                Map<String, Feature> constraintReplacementMap = new HashMap<>();
                createFeatureReplacementMap(feature, subTreeClone, constraintReplacementMap);
                constraintReplacementMap.remove(feature.getFeatureName());
                for (Constraint constraint : constraintsToClone) {
                    Constraint newConstraint = constraint.clone();
                    featureModel.getOwnConstraints().add(newConstraint);
                    adaptConstraint(subTreeClone, newConstraint, constraintReplacementMap);
                }
            }
        }
        feature.getChildren().removeAll(feature.getChildren());
        feature.getChildren().add(newChildren);
        newChildren.setParentFeature(feature);
    }

    private void addPrefixToNamesRecursively(Feature feature, String prefix) {
        feature.setFeatureName(feature.getFeatureName() + prefix);
        if (!feature.isSubmodelRoot()) {
            for (Group group : feature.getChildren()) {
                for (Feature subFeature : group.getFeatures()) {
                    addPrefixToNamesRecursively(subFeature, prefix);
                }
            }
        }
    }

    private List<Constraint> getConstraintsOnSubTree(FeatureModel featureModel, List<Feature> subTreeFeatures) {
        List<Constraint> constraints = new LinkedList<>();
        for (Constraint constraint : featureModel.getConstraints()) {
            if (constraintContains(constraint, subTreeFeatures)) {
                constraints.add(constraint);
                featureModel.getOwnConstraints().remove(constraint);
            }
        }
        return constraints;
    }

    private List<Feature> getFeatureFromSubTree(Group group) {
        List<Feature> features = new LinkedList<>();
        features.addAll(group.getFeatures());
        for (Feature subFeatures : group.getFeatures()) {
            if (!subFeatures.isSubmodelRoot()) {
                for (Group subGroup : subFeatures.getChildren()) {
                    features.addAll(getFeatureFromSubTree(subGroup));
                }
            }
        }
        return features;
    }

    private boolean constraintContains(Constraint constraint, List<Feature> subTreeFeatures) {
        List<Constraint> subParts = constraint.getConstraintSubParts();
        for (Constraint subPart : subParts) {
            if (subPart instanceof LiteralConstraint) {
                Feature feature = ((LiteralConstraint) subPart).getFeature();
                if (subTreeFeatures.contains(feature)) {
                    return true;
                }
            } else {
                constraintContains(subPart, subTreeFeatures);
            }
        }
        return false;
    }


    private void createFeatureReplacementMap(Feature oldSubTree, Feature newSubTree, Map<String, Feature> featureFeatureMap) {
        featureFeatureMap.put(oldSubTree.getFeatureName(), newSubTree);
        if (!oldSubTree.isSubmodelRoot()) {
            for (int i = 0; i < oldSubTree.getChildren().size(); i++) {
                for (int j = 0; j < oldSubTree.getChildren().get(i).getFeatures().size(); j++) {
                    createFeatureReplacementMap(oldSubTree.getChildren().get(i).getFeatures().get(j), newSubTree.getChildren().get(i).getFeatures().get(j), featureFeatureMap);
                }
            }
        }
    }

    private void adaptConstraint(Feature subTreeRoot, Constraint constraint, Map<String, Feature> featureReplacementMap) {
        List<Constraint> subParts = constraint.getConstraintSubParts();
        for (Constraint subPart : subParts) {
            if (subPart instanceof LiteralConstraint) {
                String toReplace = ((LiteralConstraint) subPart).getLiteral();
                if (featureReplacementMap.containsKey(toReplace)) {
                    LiteralConstraint subTreeRootConstraint = new LiteralConstraint(subTreeRoot.getFeatureName());
                    subTreeRootConstraint.setFeature(subTreeRoot);
                    LiteralConstraint newLiteral = new LiteralConstraint(featureReplacementMap.get(toReplace).getFeatureName());
                    newLiteral.setFeature(featureReplacementMap.get(toReplace));
                    constraint.replaceConstraintSubPart(subPart, new ParenthesisConstraint(new ImplicationConstraint(subTreeRootConstraint, newLiteral)));
                }
            } else {
                adaptConstraint(subTreeRoot, subPart, featureReplacementMap);
            }
        }
    }

    private void updateFeatureMap(FeatureModel featureModel, Feature oldSubTree, Feature newSubTree) {
        for (Group group : oldSubTree.getChildren()) {
            for (Feature subFeature : group.getFeatures()) {
                featureModel.getFeatureMap().put(subFeature.getFullReference(), subFeature);
            }
        }
    }
}
