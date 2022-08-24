package de.vill.model;

import de.vill.model.constraint.Constraint;

import java.util.List;
import java.util.Map;

/**
 * This class represents an Attribute.
 * There is an separated class and not just Objects in the attribtues map to be able to reference a singe attribute
 * for example in a constraint.
 * @param <T> The type of the value
 */
public class Attribute<T> {
    private final String name;
    private final T value;

    /**
     * The constructor of the attribute class takes an attribute name (does not contain the feature name) and a value of type T
     * @param name the name of the attribute (must be different from all other attributes of the feature)
     * @param value the value of the attribute
     */
    public Attribute(String name, T value){
        if(name == null || value == null){
            throw new IllegalArgumentException("Name and Value of an attribute must not be null!");
        }
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the value of the attribute.
     * @return Value of the attribute (never null)
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns a uvl representation of the attribute as string (different for the possible types of the value)
     * @return attribute as string
     */
    public String toString(){
        StringBuilder result = new StringBuilder();
        if(value == null){
            //should never be the case but who knows...
            return "";
        }else if(value instanceof Double){
            //double to string
            result.append(Double.toString((Double) value));
        }else if (value instanceof Integer){
            //integer to string
            result.append(Integer.toString((Integer) value));
        }else if(value instanceof Boolean){
            //boolean to string
            result.append(Boolean.toString((Boolean) value));
        }else if(value instanceof Map){
            //attributes map to string
            result.append("{");
            ((Map<?, ?>) value).forEach((k, v) -> {
                result.append(k);
                result.append(' ');
                result.append(v);
                result.append(',');
                result.append(' ');
            });
            //remove comma after last entry
            result.deleteCharAt(result.length() - 1);
            result.deleteCharAt(result.length() - 1);
            result.append("}");
        }else if(value instanceof List){
            //vector (list) of attributes to string
            result.append("[");
            for(Object item : (List)value){
                result.append(item);
                result.append(", ");
            }
            result.deleteCharAt(result.length()-1);
            result.deleteCharAt(result.length()-1);
            result.append("]");
        } else if (value instanceof String) {
            //should never be the case
            result.append((String)value);
        } else if (value instanceof Constraint) {
            result.append(((Constraint)value).toString());
        }
        return result.toString();
    }
}
