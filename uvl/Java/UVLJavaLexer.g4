lexer grammar UVLJavaLexer;

import UVLLexer;

@header {
package uvl;
}

@members {
  // A queue where extra tokens are pushed on (see the NEWLINE lexer rule).
  private java.util.LinkedList<Token> tokens = new java.util.LinkedList<>();
  // The stack that keeps track of the indentation level.
  private java.util.Stack<Integer> indents = new java.util.Stack<>();
  // The amount of opened braces, brackets and parenthesis.
  private int opened = 0;
  // The most recently produced token.
  private Token lastToken = null;
  // Whether the indentation before the first token has been handled.
  private boolean startChecked = false;

  @Override
  public void emit(Token t) {
    super.setToken(t);
    tokens.offer(t);
  }

  @Override
  public Token nextToken() {
    if (!startChecked) {
      startChecked = true;
      handleLeadingIndentation();
    }
    // Check if the end-of-file is ahead and there are still some DEDENTS expected.
    if (_input.LA(1) == EOF && !this.indents.isEmpty()) {
      // Remove any trailing EOF tokens from our buffer.
      for (int i = tokens.size() - 1; i >= 0; i--) {
        if (tokens.get(i).getType() == EOF) {
          tokens.remove(i);
        }
      }
      // First emit an extra line break that serves as the end of the statement.
      this.emit(commonToken(UVLJavaParser.NEWLINE, "\n"));
      // Now emit as much DEDENT tokens as needed.
      while (!indents.isEmpty()) {
        this.emit(createDedent());
        indents.pop();
      }
      // Put the EOF back on the token stream.
      this.emit(commonToken(UVLJavaParser.EOF, "<EOF>"));
    }

    Token next = super.nextToken();
    if (next.getChannel() == Token.DEFAULT_CHANNEL) {
      // Keep track of the last token on the default channel.
      this.lastToken = next;
    }

    return tokens.isEmpty() ? next : tokens.poll();
  }

  // Indentation before the first token is handled here, once, rather than with an
  // {atStartOfInput()}? predicate in the NEWLINE rule: a predicate reachable from the lexer
  // start state stops ANTLR from caching its DFA start state, which makes every token
  // recompute the whole ATN closure. The spaces themselves are then skipped by SKIP_.
  // Queues the same tokens the NEWLINE rule emits for a leading line: an indented first
  // line yields NEWLINE + INDENT, which the parser rejects.
  private void handleLeadingIndentation() {
    int length = 0;
    while (_input.LA(length + 1) == ' ' || _input.LA(length + 1) == '\t') {
      length++;
    }
    if (length == 0) {
      return;
    }
    int next = _input.LA(length + 1);
    int nextNext = _input.LA(length + 2);
    if (next == '\r' || next == '\n' || (next == '/' && nextNext == '/')) {
      return;
    }
    String spaces = _input.getText(org.antlr.v4.runtime.misc.Interval.of(0, length - 1));
    indents.push(getIndentationCount(spaces));
    emit(leadingToken(UVLJavaParser.NEWLINE, length - 1, length - 1, length));
    emit(leadingToken(UVLJavaParser.INDENT, 0, length - 1, length));
  }

  private CommonToken leadingToken(int type, int start, int stop, int column) {
    CommonToken token = new CommonToken(this._tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, start, stop);
    token.setCharPositionInLine(column);
    return token;
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

// Indentation-sensitive NEWLINE rule.
// No semantic predicate here: a predicate reachable from the lexer start state stops ANTLR
// from caching its DFA start state, so every token would recompute the full ATN closure.
// Indentation before the first token is handled once, in handleLeadingIndentation().
NEWLINE: ( '\r'? '\n' | '\r') SPACES? {
     String newLine = getText().replaceAll("[^\r\n]+", "");
     String spaces = getText().replaceAll("[\r\n]+", "");
     int next = _input.LA(1);
     int nextNext = _input.LA(2);

     if (opened > 0 || next == '\r' || next == '\n' || (next == '/' && nextNext == '/')) {
       // If we're inside a list or on a blank line, ignore all indents,
       // dedents and line breaks.
       skip();
     } else {
       emit(commonToken(UVLJavaParser.NEWLINE, newLine));
       int indent = getIndentationCount(spaces);
       int previous = indents.isEmpty() ? 0 : indents.peek();
       if (indent == previous) {
          // skip indents of the same size as the present indent-size
         skip();
       }
       else if (indent > previous) {
         indents.push(indent);
         emit(commonToken(UVLJavaParser.INDENT, spaces));
       }
       else {
         // Possibly emit more than 1 DEDENT token.
         while(!indents.isEmpty() && indents.peek() > indent) {
           this.emit(createDedent());
           indents.pop();
         }
       }
     }
   };

// Fragment used by NEWLINE
fragment SPACES: [ \t]+;
