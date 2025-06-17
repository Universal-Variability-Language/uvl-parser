parser grammar UVLParser;

options {
	tokenVocab = UVLLexer;
}

featureModel:
	namespace? NEWLINE? includes? NEWLINE? imports? NEWLINE? features? NEWLINE? constraints? EOF;

includes: INCLUDE_KEY NEWLINE INDENT includeLine* DEDENT;
includeLine: languageLevel NEWLINE;

namespace: NAMESPACE_KEY reference;

imports: IMPORTS_KEY NEWLINE INDENT importLine* DEDENT;
importLine: ns = reference (AS_KEY alias = reference)? NEWLINE;

features: FEATURES_KEY NEWLINE INDENT feature DEDENT;

group:
	ORGROUP groupSpec		# OrGroup
	| ALTERNATIVE groupSpec	# AlternativeGroup
	| OPTIONAL groupSpec	# OptionalGroup
	| MANDATORY groupSpec	# MandatoryGroup
	| CARDINALITY groupSpec	# CardinalityGroup;

groupSpec: NEWLINE INDENT feature+ DEDENT;

feature:
	featureType? reference featureCardinality? attributes? NEWLINE (
		INDENT group+ DEDENT
	)?;

featureCardinality: CARDINALITY_KEY CARDINALITY;

attributes:
	OPEN_BRACE (attribute (COMMA attribute)*)? CLOSE_BRACE;

attribute: valueAttribute | constraintAttribute;

valueAttribute: key value?;
key: id;
value: BOOLEAN | FLOAT | INTEGER | STRING | attributes | vector;
vector: OPEN_BRACK (value (COMMA value)*)? CLOSE_BRACK;

constraintAttribute:
	CONSTRAINTS_KEY constraint			# SingleConstraintAttribute
	| CONSTRAINTS_KEY constraintList	# ListConstraintAttribute;
constraintList:
	OPEN_BRACK (constraint (COMMA constraint)*)? CLOSE_BRACK;

constraints:
	CONSTRAINTS_KEY NEWLINE INDENT constraintLine* DEDENT;

constraintLine: constraint NEWLINE;

constraint:
	equation							# EquationConstraint
	| reference							# LiteralConstraint
	| OPEN_PAREN constraint CLOSE_PAREN	# ParenthesisConstraint
	| NOT constraint					# NotConstraint
	| constraint AND constraint			# AndConstraint
	| constraint OR constraint			# OrConstraint
	| constraint IMPLICATION constraint	# ImplicationConstraint
	| constraint EQUIVALENCE constraint	# EquivalenceConstraint;

equation:
	expression EQUAL expression				# EqualEquation
	| expression LOWER expression			# LowerEquation
	| expression GREATER expression			# GreaterEquation
	| expression LOWER_EQUALS expression	# LowerEqualsEquation
	| expression GREATER_EQUALS expression	# GreaterEqualsEquation
	| expression NOT_EQUALS expression		# NotEqualsEquation;

expression:
	FLOAT								# FloatLiteralExpression
	| INTEGER							# IntegerLiteralExpression
	| STRING							# StringLiteralExpression
	| aggregateFunction					# AggregateFunctionExpression
	| reference							# LiteralExpression
	| OPEN_PAREN expression CLOSE_PAREN	# BracketExpression
	| expression ADD expression			# AddExpression
	| expression SUB expression			# SubExpression
	| expression MUL expression			# MulExpression
	| expression DIV expression			# DivExpression;

aggregateFunction:
	SUM_KEY OPEN_PAREN (reference COMMA)? reference CLOSE_PAREN
	| AVG_KEY OPEN_PAREN (reference COMMA)? reference CLOSE_PAREN
	| stringAggregateFunction
	| numericAggregateFunction;

stringAggregateFunction:
	LEN_KEY OPEN_PAREN reference CLOSE_PAREN;

numericAggregateFunction:
	FLOOR_KEY OPEN_PAREN reference CLOSE_PAREN
	| CEIL_KEY OPEN_PAREN reference CLOSE_PAREN;

reference: (id DOT)* id;
id: ID_STRICT | ID_NOT_STRICT;

featureType: STRING_KEY | INCLUDE_KEY | BOOLEAN_KEY | REAL_KEY;

languageLevel: majorLevel (DOT (minorLevel | MUL))?;

majorLevel: BOOLEAN_KEY | ARITHMETIC_KEY | TYPE_KEY;

minorLevel:
	GROUP_CARDINALITY_KEY
	| FEATURE_CARDINALITY_KEY
	| AGGREGATE_KEY
	| STRING_CONSTRAINT_KEY;