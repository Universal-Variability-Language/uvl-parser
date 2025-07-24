lexer grammar UVLJavaLexer;

import UVLLexer;

@header {
package uvl;
}

@members {
  private java.util.LinkedList<Token> tokens = new java.util.LinkedList<>();
  private java.util.Stack<Integer> indents = new java.util.Stack<>();
  private int opened = 0;
  private Token lastToken = null;

  @Override
  public void emit(Token t) {
    super.setToken(t);
    tokens.offer(t);
  }

  @Override
  public Token nextToken() {
    if (_input.LA(1) == EOF && !this.indents.isEmpty()) {
      for (int i = tokens.size() - 1; i >= 0; i--) {
        if (tokens.get(i).getType() == EOF) {
          tokens.remove(i);
        }
      }
      this.emit(commonToken(UVLJavaParser.NEWLINE, "\n"));
      while (!indents.isEmpty()) {
        this.emit(createDedent());
        indents.pop();
      }
      this.emit(commonToken(UVLJavaParser.EOF, "<EOF>"));
    }

    Token next = super.nextToken();
    if (next.getChannel() == Token.DEFAULT_CHANNEL) {
      this.lastToken = next;
    }

    return tokens.isEmpty() ? next : tokens.poll();
  }

  private Token createDedent() {
    CommonToken dedent = commonToken(UVLJavaParser.DEDENT, "");
    dedent.setLine(this.lastToken.getLine());
    return dedent;
  }

  private CommonToken commonToken(int type, String text) {
    int stop = this.getCharIndex() - 1;
    int start = text.isEmpty() ? stop : stop - text.length() + 1;
    return new CommonToken(this._tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, start, stop);
  }

  static int getIndentationCount(String spaces) {
    int count = 0;
    for (char ch : spaces.toCharArray()) {
      switch (ch) {
        case '\t':
          count += 8 - (count % 8);
          break;
        default:
          count++;
      }
    }
    return count;
  }

  boolean atStartOfInput() {
    return super.getCharPositionInLine() == 0 && super.getLine() == 1;
  }
}

// Indentation-sensitive tokens
OPEN_PAREN: '(' {this.opened += 1;};
CLOSE_PAREN: ')' {this.opened -= 1;};
OPEN_BRACK: '[' {this.opened += 1;};
CLOSE_BRACK: ']' {this.opened -= 1;};
OPEN_BRACE: '{' {this.opened += 1;};
CLOSE_BRACE: '}' {this.opened -= 1;};
OPEN_COMMENT: '/*' {this.opened += 1;};
CLOSE_COMMENT: '*/' {this.opened -= 1;};

// Indentation-sensitive NEWLINE rule
NEWLINE: (
		{atStartOfInput()}? SPACES
		| ( '\r'? '\n' | '\r') SPACES?
	) {
     String newLine = getText().replaceAll("[^\r\n]+", "");
     String spaces = getText().replaceAll("[\r\n]+", "");
     int next = _input.LA(1);
     int nextNext = _input.LA(1);

     if (opened > 0 || next == '\r' || next == '\n' || (next == '/' && nextNext == '/')) {
       skip();
     } else {
       emit(commonToken(UVLJavaParser.NEWLINE, newLine));
       int indent = getIndentationCount(spaces);
       int previous = indents.isEmpty() ? 0 : indents.peek();
       if (indent == previous) {
         skip();
       }
       else if (indent > previous) {
         indents.push(indent);
         emit(commonToken(UVLJavaParser.INDENT, spaces));
       }
       else {
         while(!indents.isEmpty() && indents.peek() > indent) {
           this.emit(createDedent());
           indents.pop();
         }
       }
     }
   };

// Fragment used by NEWLINE
fragment SPACES: [ \t]+;
