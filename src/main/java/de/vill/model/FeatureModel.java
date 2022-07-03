package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.LinkedList;
import java.util.List;

public class FeatureModel {
    private Feature rootFeature;
    private String namespace;

    private List<Import> imports = new LinkedList<>();

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }
    public String getNamespace() {
        return namespace;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    public List<Import> getImports() {
        return imports;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        if(namespace != null) {
            result.append("namespace ");
            result.append(namespace);
            result.append(Configuration.NEWLINE);
            result.append(Configuration.NEWLINE);
        }
        if(imports.size() > 0){
            result.append("imports");
            result.append(Configuration.NEWLINE);
            for(Import importLine : imports){
                result.append(Configuration.TABULATOR);
                result.append(importLine.getNamespace());
                if(importLine.getAlias() != null){
                    result.append(" as ");
                    result.append(importLine.getAlias());
                }
                result.append(Configuration.NEWLINE);
            }
            result.append(Configuration.NEWLINE);
        }
        if(rootFeature != null) {
            result.append("features");
            result.append(Configuration.NEWLINE);
            result.append(Util.indentEachLine(getRootFeature().toString()));
        }
        return result.toString();
    }
}
