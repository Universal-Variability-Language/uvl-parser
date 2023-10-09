grammar UVLPython;
import UVLBase;

//This is here because the way python manage tabs and new lines
NEWLINE
 : ( {self.atStartOfInput()}? SPACES
   | ( '\r'? '\n' | '\r' ) SPACES?
   ) {self.handleNewline();};