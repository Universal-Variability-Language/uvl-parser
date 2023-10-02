package de.vill.util;

import de.vill.config.Configuration;

public class Util {
    public static String indentEachLine(String text) {
        StringBuilder result = new StringBuilder();
        String[] lines = text.split(Configuration.getNewlineSymbol());
        for (String line : lines) {
            result.append(Configuration.getTabulatorSymbol());
            result.append(line);
            result.append(Configuration.getNewlineSymbol());
        }
        return result.toString();
    }

    public static String addNecessaryQuotes(String reference) {
        String[] parts = reference.split("\\.");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (!part.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
                result.append("\"");
                result.append(part);
                result.append("\"");
            } else {
                result.append(part);
            }
            result.append(".");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }
}
