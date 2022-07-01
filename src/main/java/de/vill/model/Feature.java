package de.vill.model;

import de.vill.config.Configuration;

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
        result.append(Configuration.NEWLINE);
        for (Group group : children){
            String[] groupLines = group.toString().split(Configuration.NEWLINE);
            for(String line : groupLines){
                result.append(Configuration.TABULATOR);
                result.append(line);
                result.append(Configuration.NEWLINE);
            }
        }

        return result.toString();
    }
}
