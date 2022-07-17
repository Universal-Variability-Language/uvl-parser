package de.vill.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * An enum that represents all possible language levels this uvl library supports.
 */
public enum LanguageLevel {
    GROUP_CARDINALITY(2),
    FEATURE_CARDINALITY(4),
    AGGREGATE_FUNCTION(4),
    SAT_LEVEL(1),
    SMT_LEVEL(3);

    private int value;
    private static HashMap<Integer, List<LanguageLevel>> map = new HashMap<>();

    LanguageLevel(int value) {
        this.value = value;
    }

    static {
        for (LanguageLevel level : LanguageLevel.values()) {
            if(!map.containsKey(level.value)){
                map.put(level.value, new LinkedList<>());
            }
            map.get(level.value).add(level);
        }
    }

    public static List<LanguageLevel> valueOf(int languageLevel) {
        return (List<LanguageLevel>) map.get(languageLevel);
    }

    public static boolean isMajorLevel(LanguageLevel languageLevel){
        return languageLevel.getValue() % 2 != 0;
    }

    public int getValue() {
        return value;
    }
}
