package de.vill.util;

import de.vill.config.Configuration;

public class Util {
    public static String indentEachLine(String text){
        StringBuilder result = new StringBuilder();
        String[] lines = text.split(Configuration.NEWLINE);
        for(String line : lines){
            result.append(Configuration.TABULATOR);
            result.append(line);
            result.append(Configuration.NEWLINE);
        }
        return result.toString();
    }
}
