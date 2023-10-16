import re
from .UVLPythonLexer import UVLPythonLexer

from .UVLPythonParser import UVLPythonParser
from antlr4.Token import Token
from antlr4.Token import CommonToken

class UVLCustomLexer(UVLPythonLexer):
   
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
            self.emitToken(self.common_token(UVLPythonLexer.NEWLINE, "\n"));


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
      new_line = re.sub(r"[^\r\n\f]+", "", self._interp.getText(self._input)) #.replaceAll("[^\r\n\f]+", "")
      spaces = re.sub(r"[\r\n\f]+", "", self._interp.getText(self._input)) #.replaceAll("[\r\n\f]+", "")
      next = self._input.LA(1)

      if self.opened > 0 or next == '\r' or next == '\n' or next == '\f' or next == '#':
        self.skip()
      else:
        self.emitToken(self.common_token(self.NEWLINE, new_line))

        indent = self.getIndentationCount(spaces)
        if len(self.indents) == 0:
            previous = 0
        else:
            previous = self.indents[-1]

        if indent == previous:
            self.skip()
        elif indent > previous:
            self.indents.append(indent)
            self.emitToken(self.common_token(UVLPythonParser.INDENT, spaces))
        else:
            while len(self.indents) > 0 and self.indents[-1] > indent:
                self.emitToken(self.create_dedent())
                del self.indents[-1]