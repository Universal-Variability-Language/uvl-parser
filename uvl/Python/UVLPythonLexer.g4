lexer grammar UVLPythonLexer;
import UVLLexer;

OPEN_PAREN: '(' {self.opened += 1;};
CLOSE_PAREN: ')' {self.opened -= 1;};
OPEN_BRACK: '[' {self.opened += 1;};
CLOSE_BRACK: ']' {self.opened -= 1;};
OPEN_BRACE: '{' {self.opened += 1;};
CLOSE_BRACE: '}' {self.opened -= 1;};
OPEN_COMMENT: '/*' {self.opened += 1;};
CLOSE_COMMENT: '*/' {self.opened -= 1;};

//This is here because the way python manage tabs and new lines.
// No semantic predicate here: a predicate reachable from the lexer start state stops ANTLR
// from caching its DFA start state, so every token would recompute the full ATN closure.
// Indentation before the first token is handled once, in UVLCustomLexer.handleLeadingIndentation().
NEWLINE: ( '\r'? '\n' | '\r') SPACES? {self.handleNewline();};