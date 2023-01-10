package de.vill.model.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import de.vill.model.Feature;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;

public abstract class ExpressionConstraint extends Constraint {
	private Expression left;
	private Expression right;
	private String expressionSymbol;

	public ExpressionConstraint(Expression left, Expression right, String expressionSymbol) {
		this.left = left;
		this.right = right;
		this.expressionSymbol = expressionSymbol;
	}

	@Override
	public String toString(boolean withSubmodels, String currentAlias) {
		StringBuilder result = new StringBuilder();
		result.append(left.toString(withSubmodels, currentAlias));
		result.append(" ");
		result.append(expressionSymbol);
		result.append(" ");
		result.append(right.toString(withSubmodels, currentAlias));
		return result.toString();
	}

	@Override
	public List<Constraint> getConstraintSubParts() {
		return Arrays.asList();
	}

	public List<Expression> getExpressionSubParts() {
		return Arrays.asList(left, right);
	}

	public void replaceExpressionSubPart(AggregateFunctionExpression oldSubExpression, Expression newSubExpression) {
		if (left == oldSubExpression) {
			left = newSubExpression;
		} else if (right == oldSubExpression) {
			right = newSubExpression;
		}
	}

	@Override
	public void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint) {
		// no sub constraints
	}

	public boolean evaluate(Set<Feature> selectedFeatures) {
		double leftResult;
		if (left instanceof LiteralExpression && !selectedFeatures.contains(((LiteralExpression) left).getFeature())) {
			leftResult = 0;
		} else {
			leftResult = left.evaluate(selectedFeatures);
		}
		double rightResult;
		if (right instanceof LiteralExpression
				&& !selectedFeatures.contains(((LiteralExpression) right).getFeature())) {
			rightResult = 0;
		} else {
			rightResult = right.evaluate(selectedFeatures);
		}

		if ("==".equals(expressionSymbol)) {
			return leftResult == rightResult;
		}
		if ("<".equals(expressionSymbol)) {
			return leftResult < rightResult;
		}
		if (">".equals(expressionSymbol)) {
			return leftResult > rightResult;
		}
		return false;
	}

	@Override
	public Constraint clone() {
		// TODO implement clone method in expressions and clone them here
		return new EqualEquationConstraint(left, right);
	}

	@Override
	public int hashCode(int level) {
		final int prime = 31;
		int result = prime * level + (left == null ? 0 : left.hashCode());
		result = prime * result + (right == null ? 0 : right.hashCode());
		result = prime * result + (expressionSymbol == null ? 0 : expressionSymbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ExpressionConstraint other = (ExpressionConstraint) obj;
		return Objects.equals(expressionSymbol, other.expressionSymbol) && Objects.equals(left, other.left)
				&& Objects.equals(right, other.right);
	}
}
