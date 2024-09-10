grammar UVLJavaScript;
import UVLBase;

@lexer::members {
  // Una cola donde se empujan los tokens adicionales (ver la regla del lexer NEWLINE).
  let tokens = [];
  // La pila que lleva un registro del nivel de indentación.
  let indents = [];
  // La cantidad de paréntesis, corchetes y llaves abiertos.
  let opened = 0;
  // El token más recientemente producido.
  let lastToken = null;
}

OPEN_PAREN : '(' {this.opened += 1;};
CLOSE_PAREN : ')' {this.opened -= 1;};
OPEN_BRACK : '[' {this.opened += 1;};
CLOSE_BRACK : ']' {this.opened -= 1;};
OPEN_BRACE : '{' {this.opened += 1;};
CLOSE_BRACE : '}' {this.opened -= 1;};
OPEN_COMMENT: '/*' {this.opened += 1;};
CLOSE_COMMENT: '*/' {this.opened -= 1;};

NEWLINE
 : ( {this.atStartOfInput()}? SPACES
   | ( '\r'? '\n' | '\r' ) SPACES?
   ){this.handleNewline();};
