package de.vill.model;

public class Import {
    private String namespace;
    private String alias;

    private FeatureModel featureModel;

    public Import(String namespace, String alias){
        this.namespace = namespace;
        this.alias = alias;
    }
    public String getNamespace() {
        return namespace;
    }

    public String getAlias() {
        return alias;
    }

    public FeatureModel getFeatureModel(){return featureModel;}
    public void setFeatureModel(FeatureModel featureModel){
        this.featureModel = featureModel;
    }
}
