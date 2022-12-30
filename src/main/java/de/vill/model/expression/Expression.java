package de.vill.model.expression;

import java.util.List;
import java.util.Set;

import de.vill.model.Feature;

public abstract class Expression {
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	private int lineNumber;

	@Override
	public String toString() {
		return toString(true, "");
	}

	public abstract String toString(boolean withSubmodels, String currentAlias);

	public abstract List<Expression> getExpressionSubParts();

	public abstract void replaceExpressionSubPart(Expression oldSubExpression, Expression newSubExpression);

	public abstract double evaluate(Set<Feature> selectedFeatures);

	@Override
	public int hashCode() {
		return hashCode(1);
	}

	public abstract int hashCode(int level);

	@Override
	public abstract boolean equals(Object obj);
}
