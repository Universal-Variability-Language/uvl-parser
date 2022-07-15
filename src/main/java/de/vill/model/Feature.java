package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Feature {
    public String getFeatureName() {
        return featureName;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    private final String featureName;

    public String getFullReference() {
        String fullReference = "";
        int idLength = String.valueOf(Integer.MAX_VALUE).length();
        String id = String.format("%0" + idLength + "d", this.hashCode());
        if(nameSpace.equals("")){
            fullReference= featureName + "." + id;
        }else {
            fullReference= nameSpace + "." + featureName + "." + id;
        }

        return fullReference.replace('.', '_');
    }


    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    private String nameSpace = "";

    public Import getRelatedImport() {
        return relatedImport;
    }

    public void setRelatedImport(Import relatedImport) {
        this.relatedImport = relatedImport;
    }

    private Import relatedImport;
    private String lowerBound;
    private String upperBound;

    public List<Group> getChildren() {
        return children;
    }

    private List<Group> children;
    private boolean isSubmodelRoot = false;

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Attribute> attributes){
        this.attributes = attributes;
    }

    private Map<String, Attribute> attributes = new HashMap<>();

    public Feature (String name){
        this.featureName = name;
        children = new LinkedList<>();
        attributes = new HashMap<>();
    }

    public void addChildren(Group group){
        children.add(group);
    }
    @Override
    public String toString(){
        return toString(true, "");
    }

    public String toStringAsRoot(String currentAlias){
        StringBuilder result = new StringBuilder();
        result.append(getFeatureName());

        result.append(' ');
        if(!(upperBound == null & lowerBound == null)){
            result.append("cardinality [");
            if(getLowerBound().equals(getUpperBound())){
                result.append(getLowerBound());
            } else {
                result.append(getLowerBound());
                result.append("..");
                result.append(getUpperBound());
            }
            result.append("] ");
        }

        if(!attributes.isEmpty()){
            result.append("{");
            attributes.forEach((k, v) -> {
                result.append(k);
                result.append(' ');
                result.append(v);
                result.append(',');
            });
            result.deleteCharAt(result.length() - 1);
            result.append("}");
        }

        result.append(Configuration.NEWLINE);

        for (Group group : children) {
            result.append(Util.indentEachLine(group.toString(false, currentAlias)));
        }


        return result.toString();
    }

    public String getReferenceFromSpecificSubmodel(String currentAlias){
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

    public String toString(boolean withSubmodels, String currentAlias){
        StringBuilder result = new StringBuilder();
        if(withSubmodels){
            result.append(getFullReference());
        }else{
            String currentNamespace = getNameSpace().substring(currentAlias.length());
            if(currentNamespace.equals("")){
                result.append(getFeatureName());
            }else {
                if(currentNamespace.charAt(0) == '.'){
                    currentNamespace = currentNamespace.substring(1);
                }
                result.append(currentNamespace + "." + getFeatureName());
            }
        }
        result.append(' ');
        if(!(upperBound == null & lowerBound == null)){
            result.append("cardinality [");
            if(getLowerBound().equals(getUpperBound())){
                result.append(getLowerBound());
            } else {
                result.append(getLowerBound());
                result.append("..");
                result.append(getUpperBound());
            }
            result.append("] ");
        }
        if(!isSubmodelRoot() || withSubmodels) {
            if(!attributes.isEmpty()){
                result.append("{");
                attributes.forEach((k, v) -> {
                    result.append(k);
                    result.append(' ');
                    result.append(v);
                    result.append(',');
                });
                result.deleteCharAt(result.length() - 1);
                result.append("}");
            }

            result.append(Configuration.NEWLINE);

            for (Group group : children) {
                result.append(Util.indentEachLine(group.toString(withSubmodels, currentAlias)));
            }
        }

        return result.toString();
    }

    public void setAttribute(String name, Attribute value){
        attributes.put(name, value);
    }

    public Object getAttribute(String name){
        return attributes.get(name);
    }

    public boolean isSubmodelRoot() {
        return isSubmodelRoot;
    }

    public void setSubmodelRoot(boolean submodelRoot) {
        isSubmodelRoot = submodelRoot;
    }
}
