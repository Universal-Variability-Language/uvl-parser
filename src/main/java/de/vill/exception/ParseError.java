package de.vill.exception;

public class ParseError extends RuntimeException{
    public ParseError(int line, int charPositionInLine, String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
