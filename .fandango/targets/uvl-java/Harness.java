import java.nio.file.Path;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import uvl.UVLJavaLexer;
import uvl.UVLJavaParser;

/** Parses one file the way uvl-parser's own tests do: any syntax error, of the lexer or the parser, rejects it. */
public class Harness {
    static class Throwing extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object symbol, int line, int column, String msg,
                                RecognitionException e) {
            throw new IllegalStateException("line " + line + ":" + column + " " + msg);
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            UVLJavaLexer lexer = new UVLJavaLexer(CharStreams.fromPath(Path.of(args[0])));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new Throwing());
            UVLJavaParser parser = new UVLJavaParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new Throwing());
            parser.featureModel();
        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError: nesting too deep");
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getClass().getSimpleName() + ": " + String.join(" ", String.valueOf(e.getMessage()).split("\\s+")));
            System.exit(1);
        }
    }
}
