// Generated from /Users/jagalindo/Documents/uvl-parser-java/grammar/UVL.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UVLParser}.
 */
public interface UVLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link UVLParser#featureModel}.
	 * @param ctx the parse tree
	 */
	void enterFeatureModel(UVLParser.FeatureModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#featureModel}.
	 * @param ctx the parse tree
	 */
	void exitFeatureModel(UVLParser.FeatureModelContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#includes}.
	 * @param ctx the parse tree
	 */
	void enterIncludes(UVLParser.IncludesContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#includes}.
	 * @param ctx the parse tree
	 */
	void exitIncludes(UVLParser.IncludesContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#includeLine}.
	 * @param ctx the parse tree
	 */
	void enterIncludeLine(UVLParser.IncludeLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#includeLine}.
	 * @param ctx the parse tree
	 */
	void exitIncludeLine(UVLParser.IncludeLineContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#namespace}.
	 * @param ctx the parse tree
	 */
	void enterNamespace(UVLParser.NamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#namespace}.
	 * @param ctx the parse tree
	 */
	void exitNamespace(UVLParser.NamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#imports}.
	 * @param ctx the parse tree
	 */
	void enterImports(UVLParser.ImportsContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#imports}.
	 * @param ctx the parse tree
	 */
	void exitImports(UVLParser.ImportsContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#importLine}.
	 * @param ctx the parse tree
	 */
	void enterImportLine(UVLParser.ImportLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#importLine}.
	 * @param ctx the parse tree
	 */
	void exitImportLine(UVLParser.ImportLineContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#features}.
	 * @param ctx the parse tree
	 */
	void enterFeatures(UVLParser.FeaturesContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#features}.
	 * @param ctx the parse tree
	 */
	void exitFeatures(UVLParser.FeaturesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void enterOrGroup(UVLParser.OrGroupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void exitOrGroup(UVLParser.OrGroupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AlternativeGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void enterAlternativeGroup(UVLParser.AlternativeGroupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AlternativeGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void exitAlternativeGroup(UVLParser.AlternativeGroupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OptionalGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void enterOptionalGroup(UVLParser.OptionalGroupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OptionalGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void exitOptionalGroup(UVLParser.OptionalGroupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MandatoryGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void enterMandatoryGroup(UVLParser.MandatoryGroupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MandatoryGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void exitMandatoryGroup(UVLParser.MandatoryGroupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CardinalityGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void enterCardinalityGroup(UVLParser.CardinalityGroupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CardinalityGroup}
	 * labeled alternative in {@link UVLParser#group}.
	 * @param ctx the parse tree
	 */
	void exitCardinalityGroup(UVLParser.CardinalityGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#groupSpec}.
	 * @param ctx the parse tree
	 */
	void enterGroupSpec(UVLParser.GroupSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#groupSpec}.
	 * @param ctx the parse tree
	 */
	void exitGroupSpec(UVLParser.GroupSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#feature}.
	 * @param ctx the parse tree
	 */
	void enterFeature(UVLParser.FeatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#feature}.
	 * @param ctx the parse tree
	 */
	void exitFeature(UVLParser.FeatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#featureCardinality}.
	 * @param ctx the parse tree
	 */
	void enterFeatureCardinality(UVLParser.FeatureCardinalityContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#featureCardinality}.
	 * @param ctx the parse tree
	 */
	void exitFeatureCardinality(UVLParser.FeatureCardinalityContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#attributes}.
	 * @param ctx the parse tree
	 */
	void enterAttributes(UVLParser.AttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#attributes}.
	 * @param ctx the parse tree
	 */
	void exitAttributes(UVLParser.AttributesContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(UVLParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(UVLParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#valueAttribute}.
	 * @param ctx the parse tree
	 */
	void enterValueAttribute(UVLParser.ValueAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#valueAttribute}.
	 * @param ctx the parse tree
	 */
	void exitValueAttribute(UVLParser.ValueAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(UVLParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(UVLParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(UVLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(UVLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#vector}.
	 * @param ctx the parse tree
	 */
	void enterVector(UVLParser.VectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#vector}.
	 * @param ctx the parse tree
	 */
	void exitVector(UVLParser.VectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleConstraintAttribute}
	 * labeled alternative in {@link UVLParser#constraintAttribute}.
	 * @param ctx the parse tree
	 */
	void enterSingleConstraintAttribute(UVLParser.SingleConstraintAttributeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleConstraintAttribute}
	 * labeled alternative in {@link UVLParser#constraintAttribute}.
	 * @param ctx the parse tree
	 */
	void exitSingleConstraintAttribute(UVLParser.SingleConstraintAttributeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListConstraintAttribute}
	 * labeled alternative in {@link UVLParser#constraintAttribute}.
	 * @param ctx the parse tree
	 */
	void enterListConstraintAttribute(UVLParser.ListConstraintAttributeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListConstraintAttribute}
	 * labeled alternative in {@link UVLParser#constraintAttribute}.
	 * @param ctx the parse tree
	 */
	void exitListConstraintAttribute(UVLParser.ListConstraintAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#constraintList}.
	 * @param ctx the parse tree
	 */
	void enterConstraintList(UVLParser.ConstraintListContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#constraintList}.
	 * @param ctx the parse tree
	 */
	void exitConstraintList(UVLParser.ConstraintListContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#constraints}.
	 * @param ctx the parse tree
	 */
	void enterConstraints(UVLParser.ConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#constraints}.
	 * @param ctx the parse tree
	 */
	void exitConstraints(UVLParser.ConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#constraintLine}.
	 * @param ctx the parse tree
	 */
	void enterConstraintLine(UVLParser.ConstraintLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#constraintLine}.
	 * @param ctx the parse tree
	 */
	void exitConstraintLine(UVLParser.ConstraintLineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterOrConstraint(UVLParser.OrConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitOrConstraint(UVLParser.OrConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EquationConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterEquationConstraint(UVLParser.EquationConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EquationConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitEquationConstraint(UVLParser.EquationConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterLiteralConstraint(UVLParser.LiteralConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitLiteralConstraint(UVLParser.LiteralConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesisConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterParenthesisConstraint(UVLParser.ParenthesisConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesisConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitParenthesisConstraint(UVLParser.ParenthesisConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterNotConstraint(UVLParser.NotConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitNotConstraint(UVLParser.NotConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterAndConstraint(UVLParser.AndConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitAndConstraint(UVLParser.AndConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EquivalenceConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterEquivalenceConstraint(UVLParser.EquivalenceConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EquivalenceConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitEquivalenceConstraint(UVLParser.EquivalenceConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImplicationConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterImplicationConstraint(UVLParser.ImplicationConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImplicationConstraint}
	 * labeled alternative in {@link UVLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitImplicationConstraint(UVLParser.ImplicationConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqualEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterEqualEquation(UVLParser.EqualEquationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitEqualEquation(UVLParser.EqualEquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LowerEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterLowerEquation(UVLParser.LowerEquationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LowerEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitLowerEquation(UVLParser.LowerEquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterGreaterEquation(UVLParser.GreaterEquationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitGreaterEquation(UVLParser.GreaterEquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LowerEqualsEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterLowerEqualsEquation(UVLParser.LowerEqualsEquationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LowerEqualsEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitLowerEqualsEquation(UVLParser.LowerEqualsEquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterEqualsEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterGreaterEqualsEquation(UVLParser.GreaterEqualsEquationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterEqualsEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitGreaterEqualsEquation(UVLParser.GreaterEqualsEquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotEqualsEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterNotEqualsEquation(UVLParser.NotEqualsEquationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqualsEquation}
	 * labeled alternative in {@link UVLParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitNotEqualsEquation(UVLParser.NotEqualsEquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BracketExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBracketExpression(UVLParser.BracketExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BracketExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBracketExpression(UVLParser.BracketExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AggregateFunctionExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAggregateFunctionExpression(UVLParser.AggregateFunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AggregateFunctionExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAggregateFunctionExpression(UVLParser.AggregateFunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatLiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteralExpression(UVLParser.FloatLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatLiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteralExpression(UVLParser.FloatLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteralExpression(UVLParser.StringLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteralExpression(UVLParser.StringLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(UVLParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(UVLParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerLiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteralExpression(UVLParser.IntegerLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteralExpression(UVLParser.IntegerLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(UVLParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(UVLParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDivExpression(UVLParser.DivExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDivExpression(UVLParser.DivExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(UVLParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(UVLParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulExpression(UVLParser.MulExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulExpression}
	 * labeled alternative in {@link UVLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulExpression(UVLParser.MulExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SumAggregateFunction}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterSumAggregateFunction(UVLParser.SumAggregateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SumAggregateFunction}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitSumAggregateFunction(UVLParser.SumAggregateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AvgAggregateFunction}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterAvgAggregateFunction(UVLParser.AvgAggregateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AvgAggregateFunction}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitAvgAggregateFunction(UVLParser.AvgAggregateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringAggregateFunctionExpression}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterStringAggregateFunctionExpression(UVLParser.StringAggregateFunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringAggregateFunctionExpression}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitStringAggregateFunctionExpression(UVLParser.StringAggregateFunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumericAggregateFunctionExpression}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterNumericAggregateFunctionExpression(UVLParser.NumericAggregateFunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericAggregateFunctionExpression}
	 * labeled alternative in {@link UVLParser#aggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitNumericAggregateFunctionExpression(UVLParser.NumericAggregateFunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LengthAggregateFunction}
	 * labeled alternative in {@link UVLParser#stringAggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterLengthAggregateFunction(UVLParser.LengthAggregateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LengthAggregateFunction}
	 * labeled alternative in {@link UVLParser#stringAggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitLengthAggregateFunction(UVLParser.LengthAggregateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloorAggregateFunction}
	 * labeled alternative in {@link UVLParser#numericAggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterFloorAggregateFunction(UVLParser.FloorAggregateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloorAggregateFunction}
	 * labeled alternative in {@link UVLParser#numericAggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitFloorAggregateFunction(UVLParser.FloorAggregateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CeilAggregateFunction}
	 * labeled alternative in {@link UVLParser#numericAggregateFunction}.
	 * @param ctx the parse tree
	 */
	void enterCeilAggregateFunction(UVLParser.CeilAggregateFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CeilAggregateFunction}
	 * labeled alternative in {@link UVLParser#numericAggregateFunction}.
	 * @param ctx the parse tree
	 */
	void exitCeilAggregateFunction(UVLParser.CeilAggregateFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#reference}.
	 * @param ctx the parse tree
	 */
	void enterReference(UVLParser.ReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#reference}.
	 * @param ctx the parse tree
	 */
	void exitReference(UVLParser.ReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(UVLParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(UVLParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#featureType}.
	 * @param ctx the parse tree
	 */
	void enterFeatureType(UVLParser.FeatureTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#featureType}.
	 * @param ctx the parse tree
	 */
	void exitFeatureType(UVLParser.FeatureTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#languageLevel}.
	 * @param ctx the parse tree
	 */
	void enterLanguageLevel(UVLParser.LanguageLevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#languageLevel}.
	 * @param ctx the parse tree
	 */
	void exitLanguageLevel(UVLParser.LanguageLevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#majorLevel}.
	 * @param ctx the parse tree
	 */
	void enterMajorLevel(UVLParser.MajorLevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#majorLevel}.
	 * @param ctx the parse tree
	 */
	void exitMajorLevel(UVLParser.MajorLevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link UVLParser#minorLevel}.
	 * @param ctx the parse tree
	 */
	void enterMinorLevel(UVLParser.MinorLevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link UVLParser#minorLevel}.
	 * @param ctx the parse tree
	 */
	void exitMinorLevel(UVLParser.MinorLevelContext ctx);
}