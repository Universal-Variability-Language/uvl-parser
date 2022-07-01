package de.vill.model;

import de.vill.config.Configuration;

public class OptionalGroup extends Group{
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("optional");
        result.append(Configuration.NEWLINE);
        result.append(super.toString());
        return result.toString();
    }
}
