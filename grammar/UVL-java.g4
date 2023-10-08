grammar UVLPython;
import UVLbase;

//This is here because the way python manage tabs and new lines
NEWLINE
 : ( {atStartOfInput()}? SPACES
   | ( '\r'? '\n' | '\r' ) SPACES?
   ) {handleNewline();};