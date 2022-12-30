package de.vill.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * An enum that represents all possible language levels this uvl library supports.
 */
public enum LanguageLevel {
    GROUP_CARDINALITY(2, "group-cardinality"),
    FEATURE_CARDINALITY(4, "feature-cardinality"),
    AGGREGATE_FUNCTION(4, "aggregate-function"),
    SAT_LEVEL(1, "SAT-level"),
    SMT_LEVEL(3, "SMT-level");

    private int value;
 
    private String name;
    private static HashMap<Integer, List<LanguageLevel>> valueMap = new HashMap<>();
    private static HashMap<String, LanguageLevel> nameMap = new HashMap<>();

    LanguageLevel(int value, String name) {
        this.value = value;
        this.name = name;
    }

    static {
        for (LanguageLevel level : LanguageLevel.values()) {
            if(!valueMap.containsKey(level.value)){
                valueMap.put(level.value, new LinkedList<>());
            }
            valueMap.get(level.value).add(level);
            nameMap.put(level.name, level);
        }
    }

    public static List<LanguageLevel> valueOf(int languageLevel) {
        return (List<LanguageLevel>) valueMap.get(languageLevel);
    }

    public static LanguageLevel getLevelByName(String name){
        return nameMap.get(name);
    }

    public String getName(){
        return name;
    }

    public static boolean isMajorLevel(LanguageLevel languageLevel){
        return languageLevel.getValue() % 2 != 0;
    }

    public int getValue() {
        return value;
    }
}
