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

featureModel: includes? NEWLINE? namespace? NEWLINE? imports? NEWLINE? features? NEWLINE? constraints? EOF;

includes: 'include' NEWLINE INDENT includeLine* DEDENT;
includeLine: LANGUAGELEVEL NEWLINE;

namespace: 'namespace' REFERENCE;

imports: 'imports' NEWLINE INDENT importLine* DEDENT;
importLine: ns=REFERENCE ('as' alias=REFERENCE)? NEWLINE;

//TODO add possibility for multiple root features
features: 'features' NEWLINE INDENT feature DEDENT;

//rootFeature: REFERENCE NEWLINE INDENT group+ DEDENT;

group
    : ORGROUP groupSpec          # OrGroup
    | ALTERNATIVE groupSpec # AlternativeGroup
    | OPTIONAL groupSpec    # OptionalGroup
    | MANDATORY groupSpec   # MandatoryGroup
    | OPEN_BRACK lowerBound=INTEGER ('..' upperBound=(INTEGER | '*'))? CLOSE_BRACK groupSpec    # CardinalityGroup
    ;

groupSpec: NEWLINE INDENT feature+ DEDENT;

feature: REFERENCE featureCardinality? attributes? NEWLINE (INDENT group+ DEDENT)?;

featureCardinality: 'cardinality' OPEN_BRACK lowerBound=INTEGER ('..' upperBound=(INTEGER | '*'))? CLOSE_BRACK;

attributes: OPEN_BRACE (attribute (COMMA attribute)*)? CLOSE_BRACE;

attribute: key (value)?;

key: REFERENCE;
//TODO Add Float and Constraints as possible attribute
value: BOOLEAN | INTEGER | STRING | attributes | vector;
vector: OPEN_BRACK (value COMMA?)* CLOSE_BRACK;

constraints: 'constraints' NEWLINE INDENT constraintLine* DEDENT;

constraintLine: constraint NEWLINE;

constraint
    : equation                              # EquationConstraint
    | REFERENCE                             # LiteralConstraint
    | OPEN_PAREN constraint CLOSE_PAREN      # ParenthesisConstraint
    | NOT constraint                        # NotConstraint
    | constraint AND constraint             # AndConstraint
    | constraint OR constraint              # OrConstraint
    | constraint IMPLICATION constraint     # ImplicationConstraint
    | constraint EQUIVALENCE constraint     # EquivalenceConstraint
	;

equation
    : expression EQUAL expression       # EqualEquation
    | expression LOWER expression       # LowerEquation
    | expression GREATER expression     # GreaterEquation
    ;

//TODO Add float for expressions
expression:
    INTEGER                                 # IntegerLiteralExpression
    | aggregateFunction                     # AggregateFunctionExpression
    | REFERENCE                             # AttributeLiteralExpression
    | OPEN_PAREN expression CLOSE_PAREN     # BracketExpression
    | expression ADD expression             # AddExpression
    | expression SUB expression             # SubExpression
    | expression MUL expression             # MulExpression
    | expression DIV expression             # DivExpresssion
    ;

aggregateFunction
    : 'min' OPEN_PAREN (REFERENCE COMMA)? REFERENCE CLOSE_PAREN    # MinAggregateFunction
    | 'max' OPEN_PAREN (REFERENCE COMMA)? REFERENCE CLOSE_PAREN    # MaxAggregateFunction
    | 'sum' OPEN_PAREN (REFERENCE COMMA)? REFERENCE CLOSE_PAREN    # SumAggregateFunction
    | 'avg' OPEN_PAREN (REFERENCE COMMA)? REFERENCE CLOSE_PAREN    # AvgAggregateFunction
    ;

ORGROUP: 'or';
ALTERNATIVE: 'alternative';
OPTIONAL: 'optional';
MANDATORY: 'mandatory';

NOT: '!';
AND: '&';
OR: '|';
EQUIVALENCE: '<=>';
IMPLICATION: '=>';

EQUAL: '==';
LOWER: '<';
GREATER: '>';

DIV: '/';
MUL: '*';
ADD: '+';
SUB: '-';

INTEGER: '0' | [1-9][0-9]*;
BOOLEAN: 'true' | 'false';
STRING: '"'~[\r?\n]*'"';

MAJORLEVEL: 'SAT-level' | 'SMT-level';
MINORLEVEL: 'group-cardinality' | 'feature-cardinality' | 'aggregate-function';
LANGUAGELEVEL: MAJORLEVEL '.' (MINORLEVEL | '*');

REFERENCE: (ID '.')* ID;
ID: [a-zA-Z][a-zA-Z0-9_-]*;

COMMA: ',';

OPEN_PAREN : '(' {this.opened += 1;};
CLOSE_PAREN : ')' {this.opened -= 1;};
OPEN_BRACK : '[' {this.opened += 1;};
CLOSE_BRACK : ']' {this.opened -= 1;};
OPEN_BRACE : '{' {this.opened += 1;};
CLOSE_BRACE : '}' {this.opened -= 1;};
OPEN_COMMENT: '/*' {this.opened += 1;};
CLOSE_COMMENT: '*/' {this.opened -= 1;};

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