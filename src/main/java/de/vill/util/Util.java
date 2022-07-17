package de.vill.util;

import de.vill.config.Configuration;

public class Util {
    public static String indentEachLine(String text){
        StringBuilder result = new StringBuilder();
        String[] lines = text.split(Configuration.getNewlineSymbol());
        for(String line : lines){
            result.append(Configuration.getTabulatorSymbol());
            result.append(line);
            result.append(Configuration.getNewlineSymbol());
        }
        return result.toString();
    }
}
