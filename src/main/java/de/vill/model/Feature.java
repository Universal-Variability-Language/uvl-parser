package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//TODO Feature cardinality als Group modelieren, damit man feature cardinality nicht direkt unter Gruppen hat (könnte aber Probleme beim printen machen?)
/**
 * This class represents a feature of any kind (normal, numeric, abstract, ...).
 */
public class Feature {
    private final String featureName;
    private String nameSpace = "";
    private Import relatedImport;
    private String lowerBound;
    private String upperBound;
    private final List<Group> children;
    private final Map<String, Attribute> attributes;
    private boolean isSubmodelRoot = false;

    /**
     * The constructor of the feature class. Needs a name that can not be changed. The name is independent of
     * the namespace. See {@link Feature#getFeatureName()} for further explanation.
     * @param name The name of the feature (without namespace information)
     */
    public Feature (String name){
        this.featureName = name;
        children = new LinkedList<>();
        attributes = new HashMap<>();
    }

    /**
     * Returns just the name of the feature as string without any namespace information.
     * This means there are no dots in the string (because uvl does not allow such) and two different features may
     * return the same name.
     * @return The name of the feature.
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * This method returns a list with all children of the feature. Children can only be groups and not other features.
     * Only groups can have features as children. If there are no children an empty list is returned (not null).
     * This list is no clone meaning that adding / removing items will actually change the children of the feature!
     * @return A list of all children of the feature.
     */
    public List<Group> getChildren() {
        return children;
    }

    /**
     * Adds a group to the children of the feature. This could also be done by getting the child list and adding it there.
     * @param group The group that should be added as child. The group and all its children recursively must not contain
     * this feature, since this would break the tree structure which is necessary. This is not checked by this method.
     */
    public void addChildren(Group group){
        children.add(group);
    }

    /**
     * This method only returns a value if the feature is a numeric feature / has a feature cardinality. If not,
     * this method returns null.
     * @return null or the lower bound of a feature cardinality as string
     */
    public String getLowerBound() {
        return lowerBound;
    }

    /**
     * This method only returns a value if the feature is a numeric feature / has a feature cardinality. If not,
     * this method returns null. If there is no upper bound in the cardinality, this method returns the lower bound.
     * The returned value might also be the * symbol, if the upper bound is unlimited.
     * @return the upper bound as string
     */
    public String getUpperBound() {
        return upperBound;
    }

    /**
     * Sets the lower bound of a feature cardinality. Setting this value results in this feature becoming
     * a numeric feature.
     * @param lowerBound lower bound as string (must be a positive integer or zero)
     */
    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Sets the upper bound of a feature cardinality. Setting this value results in this feature becoming
     * a numeric feature.
     * @param upperBound upper bound as string (must be a positive integer or zero or the * symbol)
     */
    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * This method returns the namespace of a feature. The namespace represents the path from the root feature model
     * over all imports to the feature. Each import alias is separated by dots. If an import does not use an alias the namespace of the import
     * is used in this namespace. If the feature is in the root feature model the namespace is an empty string but not null.
     * @return the namespace of the feature (from root model to feature). May be empty but not null!
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * Set the namespace for the feature. When creating a decomposed featuremodel by yourself and not with the {@link de.vill.main.UVLModelFactory}
     * becarefull to the the namespaces and set them correct. See {@link Feature#getNameSpace()} for explanation what the
     * namespace of a feature represents.
     * @param nameSpace Namespace of the feature must not be null.
     */
    public void setNameSpace(String nameSpace) {
        if(nameSpace == null){
            throw new IllegalArgumentException("Namespace must not be null!");
        }
        this.nameSpace = nameSpace;
    }

    /**
     * This method returns a uvl valid name for the feature. This name is unique even if the feature model
     * is composed with other submodels. It does that by using the namespace (from root to feature over all imports) and
     * a unique ID of the same length to prevent naming collisions.
     * This is necessary to allow printing the composed model as one uvl model.
     * @return A uvl valid unique name for the feature
     */
    public String getFullReference() {
        String fullReference;
        //.hashCode() does vary in length, this might lead to naming conflicts, therefore make ids all of the same length by adding zeros
        int idLength = String.valueOf(Integer.MAX_VALUE).length();
        String id = String.format("%0" + idLength + "d", this.hashCode());
        if(nameSpace.equals("")){
            //this case means, the feature is in the root feature model (meaning it is no imported submodel) and therefore needs no namespace
            fullReference= featureName + "." + id;
        }else {
            //this case means, the feature is in a imported sub feature model and therefore needs a namespace to be unique
            fullReference= nameSpace + "." + featureName + "." + id;
        }
        return fullReference.replace('.', '_');
    }


    /**
     * If the feature is the root feature of an imported submodel this method returns the {@link Import} that imports the
     * corresponding feature model, if not this method returns null.
     * @return The realted import if the feature is the root feature of an imported feature model or null otherwise
     */
    public Import getRelatedImport() {
        return relatedImport;
    }

    /**
     * Set the {@link Import} that imports the feature model this feature is the root of.
     * @param relatedImport The related {@link Import}
     */
    public void setRelatedImport(Import relatedImport) {
        this.relatedImport = relatedImport;
    }

    /**
     * Returns a map with the attribute name (string) as key and the {@link Attribute} as value.
     * Returns an empty map the feature has no attributes. This is not a clone, which means editing the map
     * will actually change the feature attributes.
     * @return The attribute map.
     */
    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    /**
     * True if this feature is the root feature of an imported sub feature model, false if not.
     * @return isSubmodelRoot
     */
    public boolean isSubmodelRoot() {
        return isSubmodelRoot;
    }

    /**
     * Sets submodelRoot variable.
     * @param submodelRoot true if this feature is the root feature of an imported sub feature model, false if not.
     */
    public void setSubmodelRoot(boolean submodelRoot) {
        isSubmodelRoot = submodelRoot;
    }

    /**
     * This method is just for simplicity. It is used when printing the first feature of a composed feauturemodel.
     * @return feature in uvl representation with its subtree
     */
    @Override
    public String toString(){
        return toString(true, "");
    }

    /**
     * This method is necessary because the uvl string representation differs between the feature as imported feature
     * another feature model or as the root feature as the submodel. This method is only relevant when the decomposed
     * model is printed as String, not for the composed model. Concret this method always prints its children and attributes (if there are any)
     * even if it is an imported feature.
     * @param currentAlias The namespace under this feature should be referenced. This is necessary, because features can
     *                     be reference from e.g. constraints in other sub models, therefore the namespace can vary.
     * @return The feature and ist subtree as String
     */
    public String toStringAsRoot(String currentAlias){
        StringBuilder result = new StringBuilder();
        result.append(getFeatureName());
        result.append(cardinalityToString());
        result.append(attributesToString());
        result.append(Configuration.getNewlineSymbol());

        for (Group group : children) {
            result.append(Util.indentEachLine(group.toString(false, currentAlias)));
        }

        return result.toString();
    }


    /**
     * Returns the feature and all its children as uvl valid string.
     * @param withSubmodels true if the featuremodel is printed as composed featuremodel with all its submodels as one model,
     *                      false if the model is printed with separated sub models
     * @param currentAlias the namspace from the one referencing the feature to the feature
     * @return uvl representaiton of the feature
     */
    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        if(withSubmodels){
            result.append(getFullReference());
        }else{
            result.append(getReferenceFromSpecificSubmodel(currentAlias));
        }
        result.append(cardinalityToString());

        /*
        attributes and groups should not be printed if this is the root feature of another submodel and we want to print each submodel separately
        if the feature should be printed with is children eather call this method with withSubmodels parameter true for a composed model
        or the toStringAsRoot method if the featuremodels should be printed separately
         */
        if(!isSubmodelRoot() || withSubmodels) {
            result.append(attributesToString());
            result.append(Configuration.getNewlineSymbol());

            for (Group group : children) {
                result.append(Util.indentEachLine(group.toString(withSubmodels, currentAlias)));
            }
        }

        return result.toString();
    }

    /**
     * This method generates the namespace from which the feature is reference under the assumption that the
     * referencing is done from a feature model with the namespace currentAlias.
     * @param currentAlias The complete namespace from root model to the submodel that wants to reference this feature
     * @return the reference with name
     */
    public String getReferenceFromSpecificSubmodel(String currentAlias){
        //get the complete namespace (from root model to feature) and remove the currentAlias from its beginning -> this must not be referenced because the one referencing is already in this scope
        String currentNamespace = getNameSpace().substring(currentAlias.length());
        if(currentNamespace.equals("")){
            return getFeatureName();
        }else {
            if(currentNamespace.charAt(0) == '.'){
                currentNamespace = currentNamespace.substring(1);
            }
            return currentNamespace + "." + getFeatureName();
        }
    }

    private String cardinalityToString(){
        StringBuilder result = new StringBuilder();
        if(!(upperBound == null & lowerBound == null)){
            result.append(" cardinality [");
            if(getLowerBound().equals(getUpperBound())){
                result.append(getLowerBound());
            } else {
                result.append(getLowerBound());
                result.append("..");
                result.append(getUpperBound());
            }
            result.append("] ");
        }
        return result.toString();
    }

    private String attributesToString(){
        StringBuilder result = new StringBuilder();
        if(!attributes.isEmpty()){
            result.append(" {");
            attributes.forEach((k, v) -> {
                result.append(k);
                result.append(' ');
                result.append(v);
                result.append(',');
            });
            result.deleteCharAt(result.length() - 1);
            result.append("}");
        }
        return result.toString();
    }


}
