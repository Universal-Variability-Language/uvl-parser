package de.vill.model.expression;

import de.vill.model.Feature;
import de.vill.util.Constants;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LengthAggregateFunctionExpression extends AggregateFunctionExpression {
    private final String featureName;

    public LengthAggregateFunctionExpression(final String featureName) {
        super(featureName, null);
        this.featureName = featureName;
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return super.toString(withSubmodels, "len", currentAlias);
    }

    @Override
    public List<Expression> getExpressionSubParts() {
        return Arrays.asList();
    }

    @Override
    public String getReturnType() {
        return Constants.NUMBER;
    }

    @Override
    public double evaluate(final Set<Feature> selectedFeatures) {
        final Optional<Feature> feature = selectedFeatures.stream()
            .filter(f -> f.getFeatureName().equals(this.featureName))
            .findFirst();

        if (feature.isPresent()) {
            if (feature.get().getAttributes().containsKey("type_level_value_length")) {
                return Double.parseDouble(feature.get().getAttributes().get("type_level_value_length").getValue().toString());
            } else if (feature.get().getAttributes().containsKey("type_level_value")) {
                return feature.get().getAttributes().get("type_level_value").getValue().toString().length();
            }
        }

        return 0;
    }
}
