package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents all kinds of groups (or, alternative, mandatory, optional, cardinality)
 */
public class Group {
    /**
     * An enum with all possible group types.
     */
    public enum GroupType {
        OR,
        ALTERNATIVE,
        MANDATORY,
        OPTIONAL,
        GROUP_CARDINALITY,
        FEATURE_CARDINALITY
    }
    /// The type of the group (if type is GROUP_CARDINALITY or FEATURE_CARDINALITY lower and upper bound must be set!)
    public final GroupType GROUPTYPE;
    private final List<Feature> features;
    private String lowerBound;
    private String upperBound;

    /**
     * The constructor of the group class.
     * @param groupType The type of the group.
     */
    public Group(GroupType groupType){
        this.GROUPTYPE = groupType;
        features = new LinkedList<>();
    }

    /**
     * This method only returns a value if the group is a cardinality group. If not,
     * this method returns null.
     * @return null or the lower bound of the group cardinality as string
     */
    public String getLowerBound() {
        return lowerBound;
    }

    /**
     * Set the lower bound (only if the group is a cardinality group).
     * @param lowerBound the lower bound of the group
     */
    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * This method only returns a value if the group is a cardinality group. If not,
     * this method returns null. If there is no upper bound in the cardinality, this method returns the lower bound.
     * The returned value might also be the * symbol, if the upper bound is unlimited.
     * @return the upper bound as string
     */
    public String getUpperBound() {
        return upperBound;
    }

    /**
     * Set the upper bound of the group (only if the group is a cardinality group).
     * @param upperBound the upper bound of the group (may be * symbol)
     */
    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * Returns all children of the group (Features). If there are non an empty list is returned,
     * not null. The returned list is not a copy, which means editing this list will actually change the group.
     * @return A list with all child features.
     */
    public List<Feature> getFeatures(){
        return features;
    }

    /**
     * Returns the group and all its children as uvl valid string.
     * @param withSubmodels true if the featuremodel is printed as composed featuremodel with all its submodels as one model,
     *                      false if the model is printed with separated sub models
     * @param currentAlias the namspace from the one referencing the group (or the features in the group) to the group (or the features in the group)
     * @return uvl representaiton of the group
     */
    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();

        switch (GROUPTYPE){
            case OR:
                result.append("or");
                break;
            case ALTERNATIVE:
                result.append("alternative");
                break;
            case OPTIONAL:
                result.append("optional");
                break;
            case MANDATORY:
                result.append("mandatory");
                break;
            case GROUP_CARDINALITY:
                result.append(getCardinalityAsSting());
                break;
            case FEATURE_CARDINALITY:
                StringBuilder featureString = new StringBuilder(features.get(0).toString(withSubmodels, currentAlias));
                int indexEndFeatureName = featureString.indexOf(" ");
                featureString.insert(indexEndFeatureName, "cardinality " + getCardinalityAsSting());
                result.append(featureString);
                break;
        }
        if(!GROUPTYPE.equals(GroupType.FEATURE_CARDINALITY)) {
            result.append(Configuration.getNewlineSymbol());

            for (Feature feature : features) {
                result.append(Util.indentEachLine(feature.toString(withSubmodels, currentAlias)));
            }
        }

        return result.toString();
    }

    private String getCardinalityAsSting(){
        StringBuilder result = new StringBuilder();
        result.append("[");
        if(getLowerBound().equals(getUpperBound())){
            result.append(getLowerBound());
        } else {
            result.append(getLowerBound());
            result.append("..");
            result.append(getUpperBound());
        }
        result.append("]");
        return result.toString();
    }
}
