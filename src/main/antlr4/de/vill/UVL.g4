grammar UVL;

tokens { INDENT, DEDENT }

@lexer::members {
  // A queue where extra tokens are pushed on (see the NEWLINE lexer rule).
  private java.util.LinkedList<Token> tokens = new java.util.LinkedList<>();
  // The stack that keeps track of the indentation level.
  private java.util.Stack<Integer> indents = new java.util.Stack<>();
  // The amount of opened braces, brackets and parenthesis.
  private int opened = 0;
  // The most recently produced token.
  private Token lastToken = null;
  @Override
  public void emit(Token t) {
    super.setToken(t);
    tokens.offer(t);
  }

  @Override
  public Token nextToken() {
    // Check if the end-of-file is ahead and there are still some DEDENTS expected.
    if (_input.LA(1) == EOF && !this.indents.isEmpty()) {
      // Remove any trailing EOF tokens from our buffer.
      for (int i = tokens.size() - 1; i >= 0; i--) {
        if (tokens.get(i).getType() == EOF) {
          tokens.remove(i);
        }
      }

      // First emit an extra line break that serves as the end of the statement.
      this.emit(commonToken(UVLParser.NEWLINE, "\n"));

      // Now emit as much DEDENT tokens as needed.
      while (!indents.isEmpty()) {
        this.emit(createDedent());
        indents.pop();
      }

      // Put the EOF back on the token stream.
      this.emit(commonToken(UVLParser.EOF, "<EOF>"));
    }

    Token next = super.nextToken();

    if (next.getChannel() == Token.DEFAULT_CHANNEL) {
      // Keep track of the last token on the default channel.
      this.lastToken = next;
    }

    return tokens.isEmpty() ? next : tokens.poll();
  }

  private Token createDedent() {
    CommonToken dedent = commonToken(UVLParser.DEDENT, "");
    dedent.setLine(this.lastToken.getLine());
    return dedent;
  }

  private CommonToken commonToken(int type, String text) {
    int stop = this.getCharIndex() - 1;
    int start = text.isEmpty() ? stop : stop - text.length() + 1;
    return new CommonToken(this._tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, start, stop);
  }

  // Calculates the indentation of the provided spaces, taking the
  // following rules into account:
  //
  // "Tabs are replaced (from left to right) by one to eight spaces
  //  such that the total number of characters up to and including
  //  the replacement is a multiple of eight [...]"
  //
  //  -- https://docs.python.org/3.1/reference/lexical_analysis.html#indentation
  static int getIndentationCount(String spaces) {
    int count = 0;
    for (char ch : spaces.toCharArray()) {
      switch (ch) {
        case '\t':
          count += 8 - (count % 8);
          break;
        default:
          // A normal space char.
          count++;
      }
    }

    return count;
  }

  boolean atStartOfInput() {
    return super.getCharPositionInLine() == 0 && super.getLine() == 1;
  }
}

featureModel: namespace? NEWLINE? includes? NEWLINE? imports? NEWLINE? features? NEWLINE? constraints? EOF;

includes: 'include' NEWLINE INDENT includeLine* DEDENT;
includeLine: languageLevel NEWLINE;

namespace: 'namespace' reference;

imports: 'imports' NEWLINE INDENT importLine* DEDENT;
importLine: ns=reference ('as' alias=reference)? NEWLINE;

features: 'features' NEWLINE INDENT feature DEDENT;

group
    : ORGROUP groupSpec          # OrGroup
    | ALTERNATIVE groupSpec # AlternativeGroup
    | OPTIONAL groupSpec    # OptionalGroup
    | MANDATORY groupSpec   # MandatoryGroup
    | CARDINALITY groupSpec    # CardinalityGroup
    ;

groupSpec: NEWLINE INDENT feature+ DEDENT;

feature: featureType? reference featureCardinality? attributes? NEWLINE (INDENT group+ DEDENT)?;

featureCardinality: 'cardinality' CARDINALITY;

attributes: OPEN_BRACE (attribute (COMMA attribute)*)? CLOSE_BRACE;

attribute
    : valueAttribute
    | constraintAttribute;

valueAttribute: key value?;

key: id;
value: BOOLEAN | FLOAT | INTEGER | STRING | attributes | vector;
vector: OPEN_BRACK (value (COMMA value)*)? CLOSE_BRACK;

constraintAttribute
    : 'constraint' constraint               # SingleConstraintAttribute
    | 'constraints' constraintList          # ListConstraintAttribute
    ;
constraintList: OPEN_BRACK (constraint (COMMA constraint)*)? CLOSE_BRACK;

constraints: 'constraints' NEWLINE INDENT constraintLine* DEDENT;

constraintLine: constraint NEWLINE;

constraint
    : equation                              # EquationConstraint
    | reference                             # LiteralConstraint
    | OPEN_PAREN constraint CLOSE_PAREN     # ParenthesisConstraint
    | NOT constraint                        # NotConstraint
    | constraint AND constraint             # AndConstraint
    | constraint OR constraint              # OrConstraint
    | constraint IMPLICATION constraint     # ImplicationConstraint
    | constraint EQUIVALENCE constraint     # EquivalenceConstraint
	;

equation
    : expression EQUAL expression           # EqualEquation
    | expression LOWER expression           # LowerEquation
    | expression GREATER expression         # GreaterEquation
    | expression LOWER_EQUALS expression    # LowerEqualsEquation
    | expression GREATER_EQUALS expression  # GreaterEqualsEquation
    | expression NOT_EQUALS expression      # NotEqualsEquation
    ;

expression:
    FLOAT                                   # FloatLiteralExpression
    | INTEGER                               # IntegerLiteralExpression
    | STRING                                # StringLiteralExpression
    | aggregateFunction                     # AggregateFunctionExpression
    | reference                             # LiteralExpression
    | OPEN_PAREN expression CLOSE_PAREN     # BracketExpression
    | expression ADD expression             # AddExpression
    | expression SUB expression             # SubExpression
    | expression MUL expression             # MulExpression
    | expression DIV expression             # DivExpression
    ;

aggregateFunction
    : 'sum' OPEN_PAREN (reference COMMA)? reference CLOSE_PAREN    # SumAggregateFunction
    | 'avg' OPEN_PAREN (reference COMMA)? reference CLOSE_PAREN    # AvgAggregateFunction
    | stringAggregateFunction                                      # StringAggregateFunctionExpression
    | numericAggregateFunction                                     # NumericAggregateFunctionExpression
    ;

stringAggregateFunction
    : 'len' OPEN_PAREN reference CLOSE_PAREN        # LengthAggregateFunction
    ;

numericAggregateFunction
    : 'floor' OPEN_PAREN reference CLOSE_PAREN      # FloorAggregateFunction
    | 'ceil' OPEN_PAREN reference CLOSE_PAREN       # CeilAggregateFunction
    ;

reference: (id '.')* id;
id: ID_STRICT | ID_NOT_STRICT;
featureType: 'String' | 'Integer' | BOOLEAN_KEY | 'Real';



languageLevel: majorLevel ('.' (minorLevel | '*'))?;
majorLevel: BOOLEAN_KEY | 'Arithmetic' | 'Type';
minorLevel: 'group-cardinality' | 'feature-cardinality' | 'aggregate-function' | 'string-constraints';

ORGROUP: 'or';
ALTERNATIVE: 'alternative';
OPTIONAL: 'optional';
MANDATORY: 'mandatory';
CARDINALITY: OPEN_BRACK INTEGER ('..' (INTEGER | '*'))? CLOSE_BRACK;

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

FLOAT: '-'?[0-9]*[.][0-9]+;
INTEGER: '0' | '-'?[1-9][0-9]*;
BOOLEAN: 'true' | 'false';

BOOLEAN_KEY : 'Boolean';

COMMA: ',';

OPEN_PAREN : '(' {this.opened += 1;};
CLOSE_PAREN : ')' {this.opened -= 1;};
OPEN_BRACK : '[' {this.opened += 1;};
CLOSE_BRACK : ']' {this.opened -= 1;};
OPEN_BRACE : '{' {this.opened += 1;};
CLOSE_BRACE : '}' {this.opened -= 1;};
OPEN_COMMENT: '/*' {this.opened += 1;};
CLOSE_COMMENT: '*/' {this.opened -= 1;};

ID_NOT_STRICT: '"'~[\r\n".]+'"';
ID_STRICT: [a-zA-Z]([a-zA-Z0-9_] | '#' | '§' | '%' | '?' | '\\' | '\'' | 'ä' | 'ü' | 'ö' | 'ß' | ';')*;

STRING: '\''~[\r\n'.]+'\'';

NEWLINE
 : ( {atStartOfInput()}?   SPACES
   | ( '\r'? '\n' | '\r' ) SPACES?
   )
   {
     String newLine = getText().replaceAll("[^\r\n]+", "");
     String spaces = getText().replaceAll("[\r\n]+", "");
     int next = _input.LA(1);
     int nextNext = _input.LA(1);

     if (opened > 0 || next == '\r' || next == '\n' || next == '/' && nextNext == '/') {
       // If we're inside a list or on a blank line, ignore all indents,
       // dedents and line breaks.
       skip();
     } else {
       emit(commonToken(NEWLINE, newLine));
       int indent = getIndentationCount(spaces);
       int previous = indents.isEmpty() ? 0 : indents.peek();
       if (indent == previous) {
         // skip indents of the same size as the present indent-size
         skip();
       }
       else if (indent > previous) {
         indents.push(indent);
         emit(commonToken(UVLParser.INDENT, spaces));
       }
       else {
         // Possibly emit more than 1 DEDENT token.
         while(!indents.isEmpty() && indents.peek() > indent) {
           this.emit(createDedent());
           indents.pop();
         }
       }
     }
   }
 ;

SKIP_
  : ( SPACES | COMMENT ) -> skip
  ;

 fragment COMMENT
  : '//' ~[\r\n\f]*
  | OPEN_COMMENT .* CLOSE_COMMENT
  ;

  fragment SPACES
   : [ \t]+
   ;