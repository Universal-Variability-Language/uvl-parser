package de.vill.model;

import de.vill.config.Configuration;

public class MandatoryGroup extends Group{
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("mandatory");
        result.append(Configuration.NEWLINE);
        result.append(super.toString());
        return result.toString();
    }
}
