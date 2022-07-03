package de.vill.model;

public class Import {
    private String namespace;
    private String alias;

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
}
