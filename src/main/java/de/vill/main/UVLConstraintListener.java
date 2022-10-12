package de.vill.main;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import de.vill.UVLConstraintBaseListener;
import de.vill.UVLConstraintParser.AndConstraintContext;
import de.vill.UVLConstraintParser.AttributeLiteralExpressionContext;
import de.vill.UVLConstraintParser.AvgAggregateFunctionContext;
import de.vill.UVLConstraintParser.DivExpresssionContext;
import de.vill.UVLConstraintParser.EqualEquationContext;
import de.vill.UVLConstraintParser.EquivalenceConstraintContext;
import de.vill.UVLConstraintParser.FloatLiteralExpressionContext;
import de.vill.UVLConstraintParser.GreaterEquationContext;
import de.vill.UVLConstraintParser.IdContext;
import de.vill.UVLConstraintParser.ImplicationConstraintContext;
import de.vill.UVLConstraintParser.LiteralConstraintContext;
import de.vill.UVLConstraintParser.LowerEquationContext;
import de.vill.UVLConstraintParser.MulExpressionContext;
import de.vill.UVLConstraintParser.NotConstraintContext;
import de.vill.UVLConstraintParser.OrConstraintContext;
import de.vill.UVLConstraintParser.ParenthesisConstraintContext;
import de.vill.UVLConstraintParser.ReferenceContext;
import de.vill.UVLConstraintParser.StringContext;
import de.vill.UVLConstraintParser.SumAggregateFunctionContext;
import de.vill.model.Import;
import de.vill.model.constraint.AndConstraint;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.EqualEquationConstraint;
import de.vill.model.constraint.EquivalenceConstraint;
import de.vill.model.constraint.GreaterEquationConstraint;
import de.vill.model.constraint.ImplicationConstraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.LowerEquationConstraint;
import de.vill.model.constraint.NotConstraint;
import de.vill.model.constraint.OrConstraint;
import de.vill.model.constraint.ParenthesisConstraint;
import de.vill.model.expression.AggregateFunctionExpression;
import de.vill.model.expression.AvgAggregateFunctionExpression;
import de.vill.model.expression.DivExpression;
import de.vill.model.expression.Expression;
import de.vill.model.expression.LiteralExpression;
import de.vill.model.expression.MulExpression;
import de.vill.model.expression.NumberExpression;
import de.vill.model.expression.SumAggregateFunctionExpression;

public class UVLConstraintListener extends UVLConstraintBaseListener {
	private Stack<Constraint> constraintStack = new Stack<>();
	private Stack<Expression> expressionStack = new Stack<>();

	@Override
	public void exitString(StringContext ctx) {
		// TODO Auto-generated method stub
		super.exitString(ctx);
	}

	@Override
	public void exitFloatLiteralExpression(FloatLiteralExpressionContext ctx) {
		 Expression expression = new NumberExpression(Double.parseDouble(ctx.FLOAT().getText()));
	        expressionStack.push(expression);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        expression.setLineNumber(line);
	}

	@Override
	public void exitLiteralConstraint(LiteralConstraintContext ctx) {
		String featureReference = ctx.reference().getText().replace("\"", "");
		LiteralConstraint constraint = new LiteralConstraint(featureReference);
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitParenthesisConstraint(ParenthesisConstraintContext ctx) {
        Constraint constraint = new ParenthesisConstraint(constraintStack.pop());
        constraintStack.push(constraint);
        Token t = ctx.getStart();
        int line = t.getLine();
        constraint.setLineNumber(line);
	}

	@Override
	public void exitImplicationConstraint(ImplicationConstraintContext ctx) {
		 Constraint rightConstraint = constraintStack.pop();
	        Constraint leftConstraint = constraintStack.pop();
	        Constraint constraint = new ImplicationConstraint(leftConstraint, rightConstraint);
	        constraintStack.push(constraint);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        constraint.setLineNumber(line);
	}

	@Override
	public void exitAvgAggregateFunction(AvgAggregateFunctionContext ctx) {
		 AggregateFunctionExpression expression;
	        if(ctx.reference().size() > 1) {
	            expression = new AvgAggregateFunctionExpression(ctx.reference().get(1).getText().replace("\"", ""), ctx.reference().get(0).getText().replace("\"", ""));
	        }else {
	            expression = new AvgAggregateFunctionExpression(ctx.reference().get(0).getText().replace("\"", ""));
	        }
	        expressionStack.push(expression);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        expression.setLineNumber(line);
	}

	@Override
	public void exitAttributeLiteralExpression(AttributeLiteralExpressionContext ctx) {
		 LiteralExpression expression = new LiteralExpression(ctx.reference().getText().replace("\"", ""));
	        expressionStack.push(expression);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        expression.setLineNumber(line);
	}

	@Override
	public void exitSumAggregateFunction(SumAggregateFunctionContext ctx) {
		  AggregateFunctionExpression expression;
	        if(ctx.reference().size() > 1) {
	            expression = new SumAggregateFunctionExpression(ctx.reference().get(1).getText().replace("\"", ""), ctx.reference().get(0).getText().replace("\"", ""));
	        }else {
	            expression = new SumAggregateFunctionExpression(ctx.reference().get(0).getText().replace("\"", ""));
	        }
	        expressionStack.push(expression);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        expression.setLineNumber(line);
	}

	@Override
	public void exitDivExpresssion(DivExpresssionContext ctx) {
		 Expression right = expressionStack.pop();
	        Expression left = expressionStack.pop();
	        Expression expression = new DivExpression(left, right);
	        expressionStack.push(expression);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        expression.setLineNumber(line);
	}

	@Override
	public void exitAndConstraint(AndConstraintContext ctx) {
		Constraint rightConstraint = constraintStack.pop();
		Constraint leftConstraint = constraintStack.pop();
		Constraint constraint = new AndConstraint(leftConstraint, rightConstraint);
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitOrConstraint(OrConstraintContext ctx) {
		Constraint rightConstraint = constraintStack.pop();
		Constraint leftConstraint = constraintStack.pop();
		Constraint constraint = new OrConstraint(leftConstraint, rightConstraint);
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitEqualEquation(EqualEquationContext ctx) {
		Expression right = expressionStack.pop();
		Expression left = expressionStack.pop();
		Constraint constraint = new EqualEquationConstraint(left, right);
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitLowerEquation(LowerEquationContext ctx) {
		Expression right = expressionStack.pop();
		Expression left = expressionStack.pop();
		Constraint constraint = new LowerEquationConstraint(left, right);
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitNotConstraint(NotConstraintContext ctx) {
		Constraint constraint = new NotConstraint(constraintStack.pop());
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitEquivalenceConstraint(EquivalenceConstraintContext ctx) {
		Constraint rightConstraint = constraintStack.pop();
		Constraint leftConstraint = constraintStack.pop();
		Constraint constraint = new EquivalenceConstraint(leftConstraint, rightConstraint);
		constraintStack.push(constraint);
		Token t = ctx.getStart();
		int line = t.getLine();
		constraint.setLineNumber(line);
	}

	@Override
	public void exitMulExpression(MulExpressionContext ctx) {
		Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        Expression expression = new MulExpression(left, right);
        expressionStack.push(expression);
        Token t = ctx.getStart();
        int line = t.getLine();
        expression.setLineNumber(line);
	}

	@Override
	public void exitGreaterEquation(GreaterEquationContext ctx) {
		  Expression right = expressionStack.pop();
	        Expression left = expressionStack.pop();
	        Constraint constraint = new GreaterEquationConstraint(left, right);
	        constraintStack.push(constraint);
	        Token t = ctx.getStart();
	        int line = t.getLine();
	        constraint.setLineNumber(line);
	}

	public Constraint getConstraint() {
		return constraintStack.pop();
	}

}
