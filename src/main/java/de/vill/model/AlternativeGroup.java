package de.vill.model;

import de.vill.config.Configuration;

public class AlternativeGroup extends Group{
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("alternative");
        result.append(Configuration.NEWLINE);
        result.append(super.toString());
        return result.toString();
    }
}
