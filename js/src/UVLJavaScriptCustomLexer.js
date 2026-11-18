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
    }

    emitToken(t) {
        super.emitToken(t);
        this.tokens.push(t);
    }

    nextToken() {
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

    atStartOfInput() {
        return this._interp.column === 0 && this._interp.line === 1;
    }

    isCommentOnlyLine() {
        let offset = 1;
        const isLineEnd = code => code === antlr4.Token.EOF || code === 10 || code === 13 || code === 12;

        while (true) {
            const code = this._input.LA(offset);
            if (isLineEnd(code)) return true;
            if (code === 32 || code === 9) {
                offset += 1;
            } else if (code === 47) {
                const following = this._input.LA(offset + 1);
                if (following === 47) return true;
                if (following !== 42) return false;
                offset += 2;
                while (!isLineEnd(this._input.LA(offset))) {
                    if (this._input.LA(offset) === 42 && this._input.LA(offset + 1) === 47) {
                        offset += 2;
                        break;
                    }
                    offset += 1;
                }
            } else {
                return false;
            }
        }
    }

    handleNewline() {
        const newLine = this._interp.getText(this._input).replace(/[^\r\n\f]+/g, "");
        const spaces = this._interp.getText(this._input).replace(/[\r\n\f]+/g, "");
        if (this._input.LA(1) === 47 && this.isCommentOnlyLine()) {
            this.skip();
            return;
        }
        const next = String.fromCharCode(this._input.LA(1));

        if (this.opened > 0 || next === '\r' || next === '\n' || next === '\f' || next === '#' ||
            (this._input.LA(1) === antlr4.Token.EOF && spaces.length > 0)) {
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
