import re
from UVLLexer import UVLLexer

from UVLParser import UVLParser
from antlr4.Token import Token
from antlr4.Token import CommonToken

class PythonUVLLexer(UVLLexer):
   
    def __init__(self, input_stream):
        super().__init__(input_stream)
        self.tokens = []
        self.indents = []
        self.opened = 0
        self.lastToken = None
        
    def emitToken(self, t):
        super().emitToken(t)
        self.tokens.append(t)  

    def nextToken(self):
        # Check if the end-of-file is ahead and there are still some DEDENTS expected.
        if self._input.LA(1) == Token.EOF and len(self.indents) != 0:
            # Remove any trailing EOF tokens from our buffer.
            while len(self.tokens) > 0 and self.tokens[-1].type == Token.EOF:
                del self.tokens[-1]

            # First emit an extra line break that serves as the end of the statement.
            self.emitToken(self.common_token(UVLLexer.NEWLINE, "\n"));


            # Now emit as much DEDENT tokens as needed.
            while len(self.indents) != 0:
                self.emitToken(self.create_dedent())
                del self.indents[-1]              
            # Put the EOF back on the token stream.
            self.emitToken(self.common_token(Token.EOF, "<EOF>"));

        next_token = super().nextToken()

        if next_token.channel == Token.DEFAULT_CHANNEL:
            # Keep track of the last token on the default channel.
            self.lastToken = next_token

        return self.tokens.pop(0) if len(self.tokens) > 0 else next_token

    def create_dedent(self):
        dedent = self.common_token(self.DEDENT, "")
        dedent.line = self.lastToken.line
        return dedent

    def common_token(self, type, text):
        stop = self.getCharIndex() - 1
        start = stop - len(text) + 1 if text else stop
        return CommonToken(self._tokenFactorySourcePair, type, Token.DEFAULT_CHANNEL, start, stop)

    @staticmethod
    def getIndentationCount(spaces):
        count = 0
        for ch in spaces:
            if ch == '\t':
                count += 8 - (count % 8)
            else:  # A normal space char.
                count += 1
        return count
    
    def skipToken(self):
        self.skip()

    def atStartOfInput(self):
        return self._interp.column == 0 and self._interp.line == 1
    
    def handleNewline(self):
      print("handling new line")
      new_line = re.sub(r"[^\r\n]+", "", self._input.getText(self._tokenStartCharIndex, self._input.index))
      spaces = re.sub(r"[\r\n]+", "", self._input.getText(self._tokenStartCharIndex, self._input.index))
      next_char = self._input.LA(1)
      next_next_char = self._input.LA(2)

      print(new_line, spaces, next_char, next_next_char, self.opened)
      if self.opened > 0 or next_char in ['\r', '\n', '\r\n'] or (next_char == '/' and next_next_char == '/'):
          # If we are inside a list or on a blank line, ignore all indents,
          # dedents and line breaks.
          self.skipToken()
      else:
          jump = self.common_token(self.NEWLINE, new_line)
          self._token = jump
          print("emitting new line", self._token)
          super().emit()
          indent = self.getIndentationCount(spaces)
          
          previous = self.indents[-1] if self.indents else 0
          print(indent, previous)
          if indent == previous:
              # Skip indents of the same size as the present indent-size.
              self.skipToken()
          elif indent > previous:
              self.indents.append(indent)
              #ident=self.commonToken(UVLLexer.INDENT, spaces)
              #self._token = ident
              #super().emit()
              self.emit(self.common_token(UVLParser.INDENT, spaces))
          else:
              # Possibly emit more than 1 DEDENT token.
              while self.indents and self.indents[-1] > indent:
                  self.emit(self.create_dedent())
                  self.indents.pop()