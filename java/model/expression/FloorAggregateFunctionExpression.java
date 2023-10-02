package de.vill.model.expression;

import de.vill.model.Feature;
import de.vill.util.Constants;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FloorAggregateFunctionExpression extends AggregateFunctionExpression {
    private final String featureName;

    public FloorAggregateFunctionExpression(final String featureName) {
        super(featureName, null);
        this.featureName = featureName;
    }

    @Override
    public String toString(final boolean withSubmodels, final String currentAlias) {
        return super.toString(withSubmodels, "floor", currentAlias);
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
            if (feature.get().getAttributes().containsKey("type_level_value")) {
                return Math.floor(Double.parseDouble(feature.get().getAttributes().get("type_level_value").getValue().toString()));
            }
        }

        return 0;
    }
}
