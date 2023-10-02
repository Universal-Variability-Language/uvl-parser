package de.vill.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An enum that represents the possible types of the Feature (valid of TYPE-level)
 */
public enum FeatureType {
    STRING("String"),
    INT("Integer"),
    BOOL("Boolean"),
    REAL("Real"),
    ;

    private final String name;
    private static final List<String> supportedFeatureTypes;


    FeatureType(final String name) {
        this.name = name;
    }

    static {
        supportedFeatureTypes = Arrays.stream(FeatureType.values())
                .map(FeatureType::getName)
                .collect(Collectors.toList());
    }

    public String getName() {
        return this.name;
    }

    public static List<String> getSupportedFeatureTypes() {
        return supportedFeatureTypes;
    }

    public static FeatureType fromString(final String name) {
        for (final FeatureType featureType : FeatureType.values()) {
            if (featureType.name.equalsIgnoreCase(name)) {
                return featureType;
            }
        }
        return null;
    }

    public static Object getDefaultValue(FeatureType type) {
        if (type == BOOL) {
            return true;
        } else if (type == INT) {
            return 0;
        } else if (type == REAL) {
            return 0;
        } else  {
            return "";
        }
    }
}
