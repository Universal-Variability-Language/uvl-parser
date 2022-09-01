package de.vill.exception;

public class ParseError extends RuntimeException{

    public int getLine() {
        return line;
    }

    private int line = 0;
    public ParseError(int line, int charPositionInLine, String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.line = line;
    }
    public ParseError(String errorMessage) {
        super(errorMessage);
    }
}
