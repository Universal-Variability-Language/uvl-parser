package de.vill.model;

import java.util.LinkedList;
import java.util.List;

public class Feature {
    private final String NAME;
    private List<Group> children;

    public Feature (String name){
        this.NAME = name;
        children = new LinkedList<>();
    }

    public void addChildren(Group group){
        children.add(group);
    }
    @Override
    public String toString(){
        return NAME;
    }
}
