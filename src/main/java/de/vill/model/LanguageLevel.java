package de.vill.model;

import java.util.HashMap;

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

    LanguageLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
