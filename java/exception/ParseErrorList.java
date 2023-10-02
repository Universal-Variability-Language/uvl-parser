package de.vill.exception;

import java.util.LinkedList;
import java.util.List;

public class ParseErrorList extends ParseError {
    public List<ParseError> getErrorList() {
        return errorList;
    }

    private List<ParseError> errorList = new LinkedList<>();

    public ParseErrorList(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getMessage());
        result.append("\n");
        for (ParseError error : errorList) {
            result.append(error.toString());
            result.append("\n");
        }
        return result.toString();
    }
}
