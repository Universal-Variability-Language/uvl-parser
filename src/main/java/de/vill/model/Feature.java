package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Feature {
    public String getNAME() {
        return NAME;
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

    private final String NAME;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    private String nameSpace;

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
    private boolean isImported = false;

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Attribute> attributes){
        this.attributes = attributes;
    }

    private Map<String, Attribute> attributes = new HashMap<>();

    public Feature (String name){
        this.NAME = name;
        children = new LinkedList<>();
        attributes = new HashMap<>();
    }

    public void addChildren(Group group){
        children.add(group);
    }
    @Override
    public String toString(){
        return toString(true);
    }

    public String toString(boolean withSubmodels){
        StringBuilder result = new StringBuilder();
        if(withSubmodels){
            if(getNameSpace() != null){
                result.append(getNameSpace());
                result.append(".");
            }
            result.append(NAME);
        }else{
            result.append(NAME);
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
        if(!isImported() || withSubmodels) {
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
                result.append(Util.indentEachLine(group.toString(withSubmodels)));
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

    public boolean isImported() {
        return isImported;
    }

    public void setImported(boolean imported) {
        isImported = imported;
    }
}
