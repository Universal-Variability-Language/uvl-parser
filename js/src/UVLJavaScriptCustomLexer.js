import UVLJavaScriptLexer from './lib/UVLJavaScriptLexer.js';
import UVLJavaScriptParser from './lib/UVLJavaScriptParser.js';
import antlr4 from 'antlr4';

export default class UVLJavaScriptCustomLexer extends UVLJavaScriptLexer {

    constructor(input_stream) {
        super(input_stream);
        this.tokens = [];
        this.indents = [];
        this.opened = 0;
        this.lastToken = null;
        this.startChecked = false;
    }

    emitToken(t) {
        super.emitToken(t);
        this.tokens.push(t);
    }

    nextToken() {
        if (!this.startChecked) {
            this.startChecked = true;
            this.handleLeadingIndentation();
        }

        if (this._input.LA(1) === antlr4.Token.EOF && this.indents.length !== 0) {
            while (this.tokens.length > 0 && this.tokens[this.tokens.length - 1].type === antlr4.Token.EOF) {
                this.tokens.pop();
            }

            this.emitToken(this.commonToken(UVLJavaScriptLexer.NEWLINE, "\n"));

            while (this.indents.length !== 0) {
                this.emitToken(this.createDedent());
                this.indents.pop();
            }

            this.emitToken(this.commonToken(antlr4.Token.EOF, "<EOF>"));
        }

        const nextToken = super.nextToken();

        if (nextToken.channel === antlr4.Token.DEFAULT_CHANNEL) {
            this.lastToken = nextToken;
        }

        return this.tokens.length > 0 ? this.tokens.shift() : nextToken;
    }

    createDedent() {
        const dedent = this.commonToken(UVLJavaScriptLexer.DEDENT, "");
        dedent.line = this.lastToken.line;
        return dedent;
    }

    commonToken(type, text) {
        const stop = this.getCharIndex() - 1;
        const start = text ? stop - text.length + 1 : stop;
        return new antlr4.CommonToken(this._tokenFactorySourcePair, type, antlr4.Token.DEFAULT_CHANNEL, start, stop);
    }

    static getIndentationCount(spaces) {
        let count = 0;
        for (const ch of spaces) {
            if (ch === '\t') {
                count += 8 - (count % 8);
            } else {
                count += 1;
            }
        }
        return count;
    }

    skipToken() {
        this.skip();
    }

    // Indentation before the first token is handled here, once, rather than with an
    // {atStartOfInput()}? predicate in the NEWLINE rule: a predicate reachable from the lexer
    // start state stops ANTLR from caching its DFA start state, which makes every token
    // recompute the whole ATN closure. The spaces themselves are then skipped by SKIP_.
    // Queues the same tokens handleNewline() emits for a leading line: an indented first
    // line yields NEWLINE + INDENT, which the parser rejects.
    handleLeadingIndentation() {
        let length = 0;
        while (this._input.LA(length + 1) === 32 || this._input.LA(length + 1) === 9) {
            length += 1;
        }
        if (length === 0) {
            return;
        }
        const next = String.fromCharCode(this._input.LA(length + 1));
        if (next === '\r' || next === '\n' || next === '\f' || next === '#') {
            return;
        }
        const spaces = this._input.getText(0, length - 1);
        this.indents.push(UVLJavaScriptCustomLexer.getIndentationCount(spaces));
        this.emitToken(this.leadingToken(UVLJavaScriptLexer.NEWLINE, length - 1, length - 1, length));
        this.emitToken(this.leadingToken(UVLJavaScriptParser.INDENT, 0, length - 1, length));
    }

    leadingToken(type, start, stop, column) {
        const token = new antlr4.CommonToken(this._tokenFactorySourcePair, type, antlr4.Token.DEFAULT_CHANNEL, start, stop);
        token.column = column;
        return token;
    }

    handleNewline() {
        const newLine = this._interp.getText(this._input).replace(/[^\r\n\f]+/g, "");
        const spaces = this._interp.getText(this._input).replace(/[\r\n\f]+/g, "");
        const next = String.fromCharCode(this._input.LA(1));

        if (this.opened > 0 || next === '\r' || next === '\n' || next === '\f' || next === '#') {
            this.skip();
        } else {
            this.emitToken(this.commonToken(UVLJavaScriptLexer.NEWLINE, newLine));

            const indent = UVLJavaScriptCustomLexer.getIndentationCount(spaces);
            const previous = this.indents.length === 0 ? 0 : this.indents[this.indents.length - 1];

            if (indent === previous) {
                this.skip();
            } else if (indent > previous) {
                this.indents.push(indent);
                this.emitToken(this.commonToken(UVLJavaScriptParser.INDENT, spaces));
            } else {
                while (this.indents.length > 0 && this.indents[this.indents.length - 1] > indent) {
                    this.emitToken(this.createDedent());
                    this.indents.pop();
                }
            }
        }
    }
}
