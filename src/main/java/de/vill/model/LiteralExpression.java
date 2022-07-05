package de.vill.model;

public class LiteralExpression extends Expression{
    private String content;
    public LiteralExpression(String content){
        this.content = content;
    }

    @Override
    public String toString(){
        return content;
    }
}
