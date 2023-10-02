import re
from uvlparser.UVLLexer import UVLLexer
from antlr4.Token import Token
from antlr4.Token import CommonToken

class PythonUVLLexer(UVLLexer):
   
    def __init__(self, input_stream):
        super().__init__(input_stream)
        self.tokens = []
        self.indents = []
        self.opened = 0
        self.lastToken = None
        
    
    def emit(self,t:Token):
        super.setToken(t)
        self.tokens.offer(t)
    
    
    def nextToken(self):
        # Check if the end-of-file is ahead and there are still some DEDENTS expected.
        if self._input.LA(1) == Token.EOF and len(self.indents) != 0:
            # Remove any trailing EOF tokens from our buffer.
            self.tokens = [t for t in self.tokens if t.type != Token.EOF]

            # First emit an extra line break that serves as the end of the statement.
            self.emit(self.commonToken(self.NEWLINE, "\r\n"))

            # Now emit as much DEDENT tokens as needed.
            while len(self.indents) != 0:
                self.emit(self.createDedent())
                self.indents.pop()

            # Put the EOF back on the token stream.
            self.emit(self.commonToken(self.EOF, "<EOF>"))

        next_token = super().nextToken()

        if next_token.channel == Token.DEFAULT_CHANNEL:
            # Keep track of the last token on the default channel.
            self.lastToken = next_token

        return self.tokens.pop(0) if len(self.tokens) > 0 else next_token

    def createDedent(self):
        dedent = self.commonToken(self.DEDENT, "")
        dedent.line = self.lastToken.line
        return dedent

    def commonToken(self, type, text):
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
      new_line = re.sub(r"[^\r\n]+", "", self._input.getText(self._tokenStartCharIndex, self._input.index))
      spaces = re.sub(r"[\r\n]+", "", self._input.getText(self._tokenStartCharIndex, self._input.index))
      next_char = self._input.LA(1)
      next_next_char = self._input.LA(2)

      if self.opened > 0 or next_char in ['\r', '\n'] or (next_char == '/' and next_next_char == '/'):
          # If we are inside a list or on a blank line, ignore all indents,
          # dedents and line breaks.
          self.skipToken()
      else:
          jump = self.commonToken(self.NEWLINE, new_line)
          self._token = jump
          self.emit()
          indent = self.getIndentationCount(spaces)
          previous = self.indents[-1] if self.indents else 0

          if indent == previous:
              # Skip indents of the same size as the present indent-size.
              self.skipToken()
          elif indent > previous:
              self.indents.append(indent)
              ident=self.commonToken(UVLLexer.INDENT, spaces)
              self._token = ident
              self.emit()
          else:
              # Possibly emit more than 1 DEDENT token.
              while self.indents and self.indents[-1] > indent:
                  self.emit(self.createDedent())
                  self.indents.pop()
