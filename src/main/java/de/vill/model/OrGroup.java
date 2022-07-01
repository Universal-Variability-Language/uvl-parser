package de.vill.model;

public class OrGroup extends Group {
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("or");
        result.append("\n");
        result.append(super.toString());
        return result.toString();
    }
}
