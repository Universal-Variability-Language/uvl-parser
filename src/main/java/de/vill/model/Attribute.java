package de.vill.model;

import java.util.List;
import java.util.Map;

public class Attribute<T> {

    public Attribute(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    private T value;

    public String toString(){
        StringBuilder result = new StringBuilder();
        if(value == null){
            return "";
        }else if (value instanceof Integer){
            result.append(Integer.toString((Integer) value));
        }else if(value instanceof Boolean){
            result.append(Boolean.toString((Boolean) value));
        }else if(value instanceof Map){
            result.append("{");
            ((Map<?, ?>) value).forEach((k, v) -> {
                result.append(k);
                result.append(' ');
                result.append(v);
                result.append(',');
            });
            result.deleteCharAt(result.length() - 1);
            result.append("}");
        }else if(value instanceof List){
            result.append("[");
            for(Object item : (List)value){
                result.append(item);
                result.append(",");
            }
            result.deleteCharAt(result.length()-1);
            result.append("]");
        } else if (value instanceof String) {
            result.append((String)value);
        }
        return result.toString();
    }
}
