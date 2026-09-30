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
        self.startChecked = False
        
    def emitToken(self, t):
        super().emitToken(t)
        self.tokens.append(t)  

    def nextToken(self):
        if not self.startChecked:
            self.startChecked = True
            self.handleLeadingIndentation()

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

    def handleLeadingIndentation(self):
        # Indentation before the first token is handled here, once, rather than with a
        # {atStartOfInput()}? predicate in the NEWLINE rule: a predicate reachable from the lexer
        # start state stops ANTLR from caching its DFA start state, which makes every token
        # recompute the whole ATN closure. The spaces themselves are then skipped by SKIP_.
        # Queues the same tokens handleNewline() emits for a leading line: an indented first
        # line yields NEWLINE + INDENT, which the parser rejects.
        length = 0
        while self._input.LA(length + 1) in (ord(' '), ord('\t')):
            length += 1
        if length == 0:
            return
        next_code = self._input.LA(length + 1)
        next_char = chr(next_code) if next_code != -1 else ''
        if next_char in '\r\n\f' or next_char == '/' or next_char == '#':
            return
        spaces = self._input.getText(0, length - 1)
        self.indents.append(self.getIndentationCount(spaces))
        self.emitToken(self.leading_token(self.NEWLINE, length - 1, length - 1, length))
        self.emitToken(self.leading_token(UVLPythonParser.INDENT, 0, length - 1, length))

    def leading_token(self, type, start, stop, column):
        token = CommonToken(self._tokenFactorySourcePair, type, Token.DEFAULT_CHANNEL, start, stop)
        token.column = column
        return token

    def handleNewline(self):
      new_line = re.sub(r"[^\r\n\f]+", "", self._interp.getText(self._input)) #.replaceAll("[^\r\n\f]+", "")
      spaces = re.sub(r"[\r\n\f]+", "", self._interp.getText(self._input)) #.replaceAll("[\r\n\f]+", "")
      next_code = self._input.LA(1)
      next_char = chr(next_code) if next_code != -1 else ''

      if self.opened > 0 or next_char in '\r\n\f' or next_char == '/' or next_char == '#':
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