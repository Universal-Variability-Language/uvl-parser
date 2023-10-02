package de.vill.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.vill.util.Constants;

/**
 * An enum that represents all possible language levels this UVL library supports.
 */
public enum LanguageLevel {
    // MAJOR LEVELS (logic: val % 2 != 0)
    BOOLEAN_LEVEL(1, Constants.BOOLEAN_LEVEL),
    ARITHMETIC_LEVEL(3, Constants.ARITHMETIC_LEVEL),
    TYPE_LEVEL(5, Constants.TYPE_LEVEL),

    // MINOR LEVELS (logic: val % 2 == 0)
    GROUP_CARDINALITY(2, "group-cardinality"),
    FEATURE_CARDINALITY(4, "feature-cardinality"),
    AGGREGATE_FUNCTION(4, "aggregate-function"),
    STRING_CONSTRAINTS(6, "string-constraints"),
    NUMERIC_CONSTRAINTS(6, "numeric-constraints"),
    ;

    private final int value;
    private final String name;

    private static final HashMap<Integer, List<LanguageLevel>> valueMap = new HashMap<>();
    private static final HashMap<String, LanguageLevel> nameMap = new HashMap<>();

    LanguageLevel(final int value, final String name) {
        this.value = value;
        this.name = name;
    }

    static {
        for (final LanguageLevel level : LanguageLevel.values()) {
            if (!valueMap.containsKey(level.value)) {
                valueMap.put(level.value, new LinkedList<>());
            }
            valueMap.get(level.value).add(level);
            nameMap.put(level.name, level);
        }
    }

    public static List<LanguageLevel> valueOf(final int languageLevel) {
        return valueMap.get(languageLevel);
    }

    public static LanguageLevel getLevelByName(final String name) {
        return nameMap.get(name);
    }

    public String getName() {
        return this.name;
    }

    public static boolean isMajorLevel(final LanguageLevel languageLevel) {
        return languageLevel.getValue() % 2 != 0;
    }

    public int getValue() {
        return this.value;
    }
}
