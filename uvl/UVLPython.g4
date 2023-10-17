grammar UVLPython;
import UVLBase;

OPEN_PAREN : '(' {self.opened += 1;};
CLOSE_PAREN : ')' {self.opened -= 1;};
OPEN_BRACK : '[' {self.opened += 1;};
CLOSE_BRACK : ']' {self.opened -= 1;};
OPEN_BRACE : '{' {self.opened += 1;};
CLOSE_BRACE : '}' {self.opened -= 1;};
OPEN_COMMENT: '/*' {self.opened += 1;};
CLOSE_COMMENT: '*/' {self.opened -= 1;};

//This is here because the way python manage tabs and new lines
NEWLINE
 : ( {self.atStartOfInput()}? SPACES
   | ( '\r'? '\n' | '\r' ) SPACES?
   ) {self.handleNewline();};