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
        StringBuilder result = new StringBuilder();
        result.append(NAME);
        result.append("\n");
        for (Group group : children){
            String[] groupLines = group.toString().split("\n");
            for(String line : groupLines){
                result.append("\t");
                result.append(line);
                result.append("\n");
            }
        }

        return result.toString();
    }
}
