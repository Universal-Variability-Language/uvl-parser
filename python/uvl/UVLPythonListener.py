# Generated from uvl/UVLPython.g4 by ANTLR 4.13.1
from antlr4 import *
if "." in __name__:
    from .UVLPythonParser import UVLPythonParser
else:
    from UVLPythonParser import UVLPythonParser

# This class defines a complete listener for a parse tree produced by UVLPythonParser.
class UVLPythonListener(ParseTreeListener):

    # Enter a parse tree produced by UVLPythonParser#featureModel.
    def enterFeatureModel(self, ctx:UVLPythonParser.FeatureModelContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#featureModel.
    def exitFeatureModel(self, ctx:UVLPythonParser.FeatureModelContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#includes.
    def enterIncludes(self, ctx:UVLPythonParser.IncludesContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#includes.
    def exitIncludes(self, ctx:UVLPythonParser.IncludesContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#includeLine.
    def enterIncludeLine(self, ctx:UVLPythonParser.IncludeLineContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#includeLine.
    def exitIncludeLine(self, ctx:UVLPythonParser.IncludeLineContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#namespace.
    def enterNamespace(self, ctx:UVLPythonParser.NamespaceContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#namespace.
    def exitNamespace(self, ctx:UVLPythonParser.NamespaceContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#imports.
    def enterImports(self, ctx:UVLPythonParser.ImportsContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#imports.
    def exitImports(self, ctx:UVLPythonParser.ImportsContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#importLine.
    def enterImportLine(self, ctx:UVLPythonParser.ImportLineContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#importLine.
    def exitImportLine(self, ctx:UVLPythonParser.ImportLineContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#features.
    def enterFeatures(self, ctx:UVLPythonParser.FeaturesContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#features.
    def exitFeatures(self, ctx:UVLPythonParser.FeaturesContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#OrGroup.
    def enterOrGroup(self, ctx:UVLPythonParser.OrGroupContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#OrGroup.
    def exitOrGroup(self, ctx:UVLPythonParser.OrGroupContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#AlternativeGroup.
    def enterAlternativeGroup(self, ctx:UVLPythonParser.AlternativeGroupContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#AlternativeGroup.
    def exitAlternativeGroup(self, ctx:UVLPythonParser.AlternativeGroupContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#OptionalGroup.
    def enterOptionalGroup(self, ctx:UVLPythonParser.OptionalGroupContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#OptionalGroup.
    def exitOptionalGroup(self, ctx:UVLPythonParser.OptionalGroupContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#MandatoryGroup.
    def enterMandatoryGroup(self, ctx:UVLPythonParser.MandatoryGroupContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#MandatoryGroup.
    def exitMandatoryGroup(self, ctx:UVLPythonParser.MandatoryGroupContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#CardinalityGroup.
    def enterCardinalityGroup(self, ctx:UVLPythonParser.CardinalityGroupContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#CardinalityGroup.
    def exitCardinalityGroup(self, ctx:UVLPythonParser.CardinalityGroupContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#groupSpec.
    def enterGroupSpec(self, ctx:UVLPythonParser.GroupSpecContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#groupSpec.
    def exitGroupSpec(self, ctx:UVLPythonParser.GroupSpecContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#feature.
    def enterFeature(self, ctx:UVLPythonParser.FeatureContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#feature.
    def exitFeature(self, ctx:UVLPythonParser.FeatureContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#featureCardinality.
    def enterFeatureCardinality(self, ctx:UVLPythonParser.FeatureCardinalityContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#featureCardinality.
    def exitFeatureCardinality(self, ctx:UVLPythonParser.FeatureCardinalityContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#attributes.
    def enterAttributes(self, ctx:UVLPythonParser.AttributesContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#attributes.
    def exitAttributes(self, ctx:UVLPythonParser.AttributesContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#attribute.
    def enterAttribute(self, ctx:UVLPythonParser.AttributeContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#attribute.
    def exitAttribute(self, ctx:UVLPythonParser.AttributeContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#valueAttribute.
    def enterValueAttribute(self, ctx:UVLPythonParser.ValueAttributeContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#valueAttribute.
    def exitValueAttribute(self, ctx:UVLPythonParser.ValueAttributeContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#key.
    def enterKey(self, ctx:UVLPythonParser.KeyContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#key.
    def exitKey(self, ctx:UVLPythonParser.KeyContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#value.
    def enterValue(self, ctx:UVLPythonParser.ValueContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#value.
    def exitValue(self, ctx:UVLPythonParser.ValueContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#vector.
    def enterVector(self, ctx:UVLPythonParser.VectorContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#vector.
    def exitVector(self, ctx:UVLPythonParser.VectorContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#SingleConstraintAttribute.
    def enterSingleConstraintAttribute(self, ctx:UVLPythonParser.SingleConstraintAttributeContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#SingleConstraintAttribute.
    def exitSingleConstraintAttribute(self, ctx:UVLPythonParser.SingleConstraintAttributeContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#ListConstraintAttribute.
    def enterListConstraintAttribute(self, ctx:UVLPythonParser.ListConstraintAttributeContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#ListConstraintAttribute.
    def exitListConstraintAttribute(self, ctx:UVLPythonParser.ListConstraintAttributeContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#constraintList.
    def enterConstraintList(self, ctx:UVLPythonParser.ConstraintListContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#constraintList.
    def exitConstraintList(self, ctx:UVLPythonParser.ConstraintListContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#constraints.
    def enterConstraints(self, ctx:UVLPythonParser.ConstraintsContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#constraints.
    def exitConstraints(self, ctx:UVLPythonParser.ConstraintsContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#constraintLine.
    def enterConstraintLine(self, ctx:UVLPythonParser.ConstraintLineContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#constraintLine.
    def exitConstraintLine(self, ctx:UVLPythonParser.ConstraintLineContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#OrConstraint.
    def enterOrConstraint(self, ctx:UVLPythonParser.OrConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#OrConstraint.
    def exitOrConstraint(self, ctx:UVLPythonParser.OrConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#EquationConstraint.
    def enterEquationConstraint(self, ctx:UVLPythonParser.EquationConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#EquationConstraint.
    def exitEquationConstraint(self, ctx:UVLPythonParser.EquationConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#LiteralConstraint.
    def enterLiteralConstraint(self, ctx:UVLPythonParser.LiteralConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#LiteralConstraint.
    def exitLiteralConstraint(self, ctx:UVLPythonParser.LiteralConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#ParenthesisConstraint.
    def enterParenthesisConstraint(self, ctx:UVLPythonParser.ParenthesisConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#ParenthesisConstraint.
    def exitParenthesisConstraint(self, ctx:UVLPythonParser.ParenthesisConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#NotConstraint.
    def enterNotConstraint(self, ctx:UVLPythonParser.NotConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#NotConstraint.
    def exitNotConstraint(self, ctx:UVLPythonParser.NotConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#AndConstraint.
    def enterAndConstraint(self, ctx:UVLPythonParser.AndConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#AndConstraint.
    def exitAndConstraint(self, ctx:UVLPythonParser.AndConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#EquivalenceConstraint.
    def enterEquivalenceConstraint(self, ctx:UVLPythonParser.EquivalenceConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#EquivalenceConstraint.
    def exitEquivalenceConstraint(self, ctx:UVLPythonParser.EquivalenceConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#ImplicationConstraint.
    def enterImplicationConstraint(self, ctx:UVLPythonParser.ImplicationConstraintContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#ImplicationConstraint.
    def exitImplicationConstraint(self, ctx:UVLPythonParser.ImplicationConstraintContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#EqualEquation.
    def enterEqualEquation(self, ctx:UVLPythonParser.EqualEquationContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#EqualEquation.
    def exitEqualEquation(self, ctx:UVLPythonParser.EqualEquationContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#LowerEquation.
    def enterLowerEquation(self, ctx:UVLPythonParser.LowerEquationContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#LowerEquation.
    def exitLowerEquation(self, ctx:UVLPythonParser.LowerEquationContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#GreaterEquation.
    def enterGreaterEquation(self, ctx:UVLPythonParser.GreaterEquationContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#GreaterEquation.
    def exitGreaterEquation(self, ctx:UVLPythonParser.GreaterEquationContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#LowerEqualsEquation.
    def enterLowerEqualsEquation(self, ctx:UVLPythonParser.LowerEqualsEquationContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#LowerEqualsEquation.
    def exitLowerEqualsEquation(self, ctx:UVLPythonParser.LowerEqualsEquationContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#GreaterEqualsEquation.
    def enterGreaterEqualsEquation(self, ctx:UVLPythonParser.GreaterEqualsEquationContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#GreaterEqualsEquation.
    def exitGreaterEqualsEquation(self, ctx:UVLPythonParser.GreaterEqualsEquationContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#NotEqualsEquation.
    def enterNotEqualsEquation(self, ctx:UVLPythonParser.NotEqualsEquationContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#NotEqualsEquation.
    def exitNotEqualsEquation(self, ctx:UVLPythonParser.NotEqualsEquationContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#BracketExpression.
    def enterBracketExpression(self, ctx:UVLPythonParser.BracketExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#BracketExpression.
    def exitBracketExpression(self, ctx:UVLPythonParser.BracketExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#AggregateFunctionExpression.
    def enterAggregateFunctionExpression(self, ctx:UVLPythonParser.AggregateFunctionExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#AggregateFunctionExpression.
    def exitAggregateFunctionExpression(self, ctx:UVLPythonParser.AggregateFunctionExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#FloatLiteralExpression.
    def enterFloatLiteralExpression(self, ctx:UVLPythonParser.FloatLiteralExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#FloatLiteralExpression.
    def exitFloatLiteralExpression(self, ctx:UVLPythonParser.FloatLiteralExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#StringLiteralExpression.
    def enterStringLiteralExpression(self, ctx:UVLPythonParser.StringLiteralExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#StringLiteralExpression.
    def exitStringLiteralExpression(self, ctx:UVLPythonParser.StringLiteralExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#AddExpression.
    def enterAddExpression(self, ctx:UVLPythonParser.AddExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#AddExpression.
    def exitAddExpression(self, ctx:UVLPythonParser.AddExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#IntegerLiteralExpression.
    def enterIntegerLiteralExpression(self, ctx:UVLPythonParser.IntegerLiteralExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#IntegerLiteralExpression.
    def exitIntegerLiteralExpression(self, ctx:UVLPythonParser.IntegerLiteralExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#LiteralExpression.
    def enterLiteralExpression(self, ctx:UVLPythonParser.LiteralExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#LiteralExpression.
    def exitLiteralExpression(self, ctx:UVLPythonParser.LiteralExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#DivExpression.
    def enterDivExpression(self, ctx:UVLPythonParser.DivExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#DivExpression.
    def exitDivExpression(self, ctx:UVLPythonParser.DivExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#SubExpression.
    def enterSubExpression(self, ctx:UVLPythonParser.SubExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#SubExpression.
    def exitSubExpression(self, ctx:UVLPythonParser.SubExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#MulExpression.
    def enterMulExpression(self, ctx:UVLPythonParser.MulExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#MulExpression.
    def exitMulExpression(self, ctx:UVLPythonParser.MulExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#SumAggregateFunction.
    def enterSumAggregateFunction(self, ctx:UVLPythonParser.SumAggregateFunctionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#SumAggregateFunction.
    def exitSumAggregateFunction(self, ctx:UVLPythonParser.SumAggregateFunctionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#AvgAggregateFunction.
    def enterAvgAggregateFunction(self, ctx:UVLPythonParser.AvgAggregateFunctionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#AvgAggregateFunction.
    def exitAvgAggregateFunction(self, ctx:UVLPythonParser.AvgAggregateFunctionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#StringAggregateFunctionExpression.
    def enterStringAggregateFunctionExpression(self, ctx:UVLPythonParser.StringAggregateFunctionExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#StringAggregateFunctionExpression.
    def exitStringAggregateFunctionExpression(self, ctx:UVLPythonParser.StringAggregateFunctionExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#NumericAggregateFunctionExpression.
    def enterNumericAggregateFunctionExpression(self, ctx:UVLPythonParser.NumericAggregateFunctionExpressionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#NumericAggregateFunctionExpression.
    def exitNumericAggregateFunctionExpression(self, ctx:UVLPythonParser.NumericAggregateFunctionExpressionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#LengthAggregateFunction.
    def enterLengthAggregateFunction(self, ctx:UVLPythonParser.LengthAggregateFunctionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#LengthAggregateFunction.
    def exitLengthAggregateFunction(self, ctx:UVLPythonParser.LengthAggregateFunctionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#FloorAggregateFunction.
    def enterFloorAggregateFunction(self, ctx:UVLPythonParser.FloorAggregateFunctionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#FloorAggregateFunction.
    def exitFloorAggregateFunction(self, ctx:UVLPythonParser.FloorAggregateFunctionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#CeilAggregateFunction.
    def enterCeilAggregateFunction(self, ctx:UVLPythonParser.CeilAggregateFunctionContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#CeilAggregateFunction.
    def exitCeilAggregateFunction(self, ctx:UVLPythonParser.CeilAggregateFunctionContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#reference.
    def enterReference(self, ctx:UVLPythonParser.ReferenceContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#reference.
    def exitReference(self, ctx:UVLPythonParser.ReferenceContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#id.
    def enterId(self, ctx:UVLPythonParser.IdContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#id.
    def exitId(self, ctx:UVLPythonParser.IdContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#featureType.
    def enterFeatureType(self, ctx:UVLPythonParser.FeatureTypeContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#featureType.
    def exitFeatureType(self, ctx:UVLPythonParser.FeatureTypeContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#languageLevel.
    def enterLanguageLevel(self, ctx:UVLPythonParser.LanguageLevelContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#languageLevel.
    def exitLanguageLevel(self, ctx:UVLPythonParser.LanguageLevelContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#majorLevel.
    def enterMajorLevel(self, ctx:UVLPythonParser.MajorLevelContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#majorLevel.
    def exitMajorLevel(self, ctx:UVLPythonParser.MajorLevelContext):
        pass


    # Enter a parse tree produced by UVLPythonParser#minorLevel.
    def enterMinorLevel(self, ctx:UVLPythonParser.MinorLevelContext):
        pass

    # Exit a parse tree produced by UVLPythonParser#minorLevel.
    def exitMinorLevel(self, ctx:UVLPythonParser.MinorLevelContext):
        pass



del UVLPythonParser