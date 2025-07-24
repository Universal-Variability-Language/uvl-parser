lexer grammar UVLLexer;

INDENT: '<INDENT>';
DEDENT: '<DEDENT>';

INCLUDE_KEY: 'include';
FEATURES_KEY: 'features';
IMPORTS_KEY: 'imports';
NAMESPACE_KEY: 'namespace';
AS_KEY: 'as';
CONSTRAINTS_KEY: 'constraints';
CARDINALITY_KEY: 'cardinality';
STRING_KEY: 'String';
BOOLEAN_KEY: 'Boolean';
INTEGER_KEY: 'Integer';
REAL_KEY: 'Real';
LEN_KEY: 'len';
SUM_KEY: 'sum';
AVG_KEY: 'avg';
FLOOR_KEY: 'floor';
CEIL_KEY: 'ceil';
TYPE_KEY: 'type';
ARITHMETIC_KEY: 'arithmetic';
GROUP_CARDINALITY_KEY: 'group-cardinality';
FEATURE_CARDINALITY_KEY: 'feature-cardinality';
AGGREGATE_KEY: 'aggregate-function';
STRING_CONSTRAINT_KEY: 'string-constraint';

ORGROUP: 'or';
ALTERNATIVE: 'alternative';
OPTIONAL: 'optional';
MANDATORY: 'mandatory';

CARDINALITY: '[' INTEGER ('..' (INTEGER | '*'))? ']';

NOT: '!';
AND: '&';
OR: '|';
EQUIVALENCE: '<=>';
IMPLICATION: '=>';

EQUAL: '==';
LOWER: '<';
LOWER_EQUALS: '<=';
GREATER: '>';
GREATER_EQUALS: '>=';
NOT_EQUALS: '!=';

DIV: '/';
MUL: '*';
ADD: '+';
SUB: '-';

OPEN_PAREN: '(';
CLOSE_PAREN: ')';
OPEN_BRACK: '[';
CLOSE_BRACK: ']';
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
OPEN_COMMENT: '/*';
CLOSE_COMMENT: '*/';

FLOAT: '-'? [0-9]* [.][0-9]+;
INTEGER: '0' | '-'? [1-9][0-9]*;
BOOLEAN: 'true' | 'false';

COMMA: ',';
DOT: '.';

ID_NOT_STRICT: '"' ~[\r\n".]+ '"';
ID_STRICT: [a-zA-Z]([a-zA-Z0-9_#§%?\\'äüöß;])*;

STRING: '\'' ~[\r\n']+ '\'';

SKIP_: ( SPACES | COMMENT) -> skip;

fragment COMMENT:
	'//' ~[\r\n\f]*
	| OPEN_COMMENT .* CLOSE_COMMENT;
fragment SPACES: [ \t]+;

NEWLINE: ('\r'? '\n' | '\r');
