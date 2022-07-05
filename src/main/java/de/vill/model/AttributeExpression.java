package de.vill.model;

public class AttributeExpression extends Expression{
    private String content;
    public AttributeExpression(String content){
        this.content = content;
    }

    @Override
    public String toString(){
        return content;
    }
}
