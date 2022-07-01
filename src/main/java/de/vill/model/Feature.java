package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Feature {
    private final String NAME;
    private List<Group> children;

    private Map<String, Object> attributes;

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
        StringBuilder result = new StringBuilder();
        result.append(NAME);
        result.append(' ');
        attributes.forEach((k, v) -> {
            result.append(k);
            result.append(' ');
            result.append(v);
            result.append(',');
        });
        result.append(Configuration.NEWLINE);
        for (Group group : children){
            result.append(Util.indentEachLine(group.toString()));
        }

        return result.toString();
    }

    public void setAttribute(String name, Object value){
        attributes.put(name, value);
    }

    public Object getAttribute(String name){
        return attributes.get(name);
    }
}
