package de.vill.conversion;

import de.vill.exception.ParseError;
import de.vill.model.FeatureModel;
import de.vill.model.LanguageLevel;
import de.vill.model.constraint.AssignmentEquationConstraint;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.EqualEquationConstraint;
import de.vill.model.constraint.GreaterEquationConstraint;
import de.vill.model.constraint.LowerEquationConstraint;
import de.vill.model.constraint.NotConstraint;
import de.vill.model.constraint.NumericFeatureConstraint;
import de.vill.model.constraint.OrConstraint;
import de.vill.model.constraint.StringFeatureConstraint;
import de.vill.model.expression.LiteralExpression;
import de.vill.model.typelevel.NumericFeatureAssignmentConstraint;
import de.vill.model.typelevel.NumericFeatureEqualsConstraint;
import de.vill.model.typelevel.NumericFeatureGreaterConstraint;
import de.vill.model.typelevel.NumericFeatureGreaterEqualsConstraint;
import de.vill.model.typelevel.NumericFeatureLowerConstraint;
import de.vill.model.typelevel.NumericFeatureLowerEqualsConstraint;
import de.vill.model.typelevel.NumericFeatureNotEqualsConstraint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvertNumericValidityCheck implements IConversionStrategy {
    @Override
    public Set<LanguageLevel> getLevelsToBeRemoved() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.NUMERIC_VALIDITY_CHECK));
    }

    @Override
    public Set<LanguageLevel> getTargetLevelsOfConversion() {
        return new HashSet<>(Collections.singletonList(LanguageLevel.TYPE_LEVEL));
    }

    @Override
    public void convertFeatureModel(final FeatureModel rootFeatureModel, final FeatureModel featureModel) {
        processConstraints(featureModel);
    }

    private void processConstraints(FeatureModel fm) {
        List<Constraint> constraints = new ArrayList<>();
        for (Constraint constraint : fm.getOwnConstraints()) {
            if (constraint instanceof StringFeatureConstraint) {
                constraints.add(processSimpleConstraint(fm, constraint));
            } else {
                constraints.add(processCompoundConstraint(fm, constraint));
            }
        }
        if (constraints.size() != 0) {
            fm.getOwnConstraints().clear();
            fm.getOwnConstraints().addAll(constraints);
        }
    }

    private Constraint processSimpleConstraint(FeatureModel fm, Constraint constraint) {
        Boolean isRightConstant = ((NumericFeatureConstraint) constraint).getIsRightConstant();
        if (constraint instanceof NumericFeatureAssignmentConstraint) {
            return new AssignmentEquationConstraint(
                new LiteralExpression(((NumericFeatureAssignmentConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                new LiteralExpression(
                    ((NumericFeatureAssignmentConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_default_value"))
            );
        } else if (constraint instanceof NumericFeatureEqualsConstraint) {
            return new EqualEquationConstraint(
                new LiteralExpression(((NumericFeatureEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                new LiteralExpression(
                    ((NumericFeatureEqualsConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_default_value"))
            );
        } else if (constraint instanceof NumericFeatureNotEqualsConstraint) {
            return new NotConstraint(
                new EqualEquationConstraint(
                    new LiteralExpression(((NumericFeatureNotEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                    new LiteralExpression(
                        ((NumericFeatureNotEqualsConstraint) constraint).getRight().getLiteral() +
                            (isRightConstant ? "" : ".type_level_default_value"))
                )
            );
        } else if (constraint instanceof NumericFeatureGreaterConstraint) {
            return new GreaterEquationConstraint(
                new LiteralExpression(((NumericFeatureGreaterConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                new LiteralExpression(
                    ((NumericFeatureGreaterConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_default_value"))
            );
        } else if (constraint instanceof NumericFeatureGreaterEqualsConstraint) {
            return new OrConstraint(
                new EqualEquationConstraint(
                    new LiteralExpression(((NumericFeatureGreaterEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                    new LiteralExpression(
                        ((NumericFeatureGreaterEqualsConstraint) constraint).getRight().getLiteral() +
                            (isRightConstant ? "" : ".type_level_default_value"))
                ),
                new GreaterEquationConstraint(
                    new LiteralExpression(((NumericFeatureGreaterEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                    new LiteralExpression(
                        ((NumericFeatureGreaterEqualsConstraint) constraint).getRight().getLiteral() +
                            (isRightConstant ? "" : ".type_level_default_value"))
                )
            );
        } else if (constraint instanceof NumericFeatureLowerConstraint) {
            return new LowerEquationConstraint(
                new LiteralExpression(((NumericFeatureLowerConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                new LiteralExpression(
                    ((NumericFeatureLowerConstraint) constraint).getRight().getLiteral() + (isRightConstant ? "" : ".type_level_default_value"))
            );
        } else if (constraint instanceof NumericFeatureLowerEqualsConstraint) {
            return new OrConstraint(
                new EqualEquationConstraint(
                    new LiteralExpression(((NumericFeatureLowerEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                    new LiteralExpression(
                        ((NumericFeatureLowerEqualsConstraint) constraint).getRight().getLiteral() +
                            (isRightConstant ? "" : ".type_level_default_value"))
                ),
                new LowerEquationConstraint(
                    new LiteralExpression(((NumericFeatureLowerEqualsConstraint) constraint).getLeft().getLiteral() + ".type_level_default_value"),
                    new LiteralExpression(
                        ((NumericFeatureLowerEqualsConstraint) constraint).getRight().getLiteral() +
                            (isRightConstant ? "" : ".type_level_default_value"))
                )
            );
        }

        // should not come in an ideal scenario
        throw new ParseError("constraint type not supported");
    }

    private Constraint processCompoundConstraint(FeatureModel fm, Constraint constraint) {
        if (constraint instanceof NumericFeatureConstraint) {
            return processSimpleConstraint(fm, constraint);
        }

        constraint.getConstraintSubParts().forEach(c -> c.replaceConstraintSubPart(c, processCompoundConstraint(fm, c)));

        return constraint;
    }
}
