package de.vill.main;

import de.vill.UVLLexer;
import de.vill.UVLParser;
import de.vill.exception.ParseError;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Import;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class UVLModelFactory {

    public FeatureModel parse(String text, Map<String, String> fileLoader) throws ParseError{
        return parseFeatureModelWithImports(text,fileLoader, new HashMap<>());
    }

    public FeatureModel parseFeatureModelWithImports(String text, Map<String, String> fileLoader, Map<String, Import> visitedImports){
        UVLLexer uvlLexer = new UVLLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(uvlLexer);
        UVLParser uvlParser = new UVLParser(tokens);

        uvlParser.addErrorListener(new BaseErrorListener(){
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new ParseError(line, charPositionInLine,"failed to parse at line " + line + ":" + charPositionInLine + " due to " + msg, e);
            }
        });

        UVLListener uvlListener = new UVLListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(uvlListener, uvlParser.featureModel());

        FeatureModel featureModel = uvlListener.getFeatureModel();
        featureModel.getOwnConstraints().addAll(featureModel.getConstraints());

        visitedImports.put(featureModel.getNamespace(), null);

        for(Import importLine : featureModel.getImports()){
            if(visitedImports.containsKey(importLine.getNamespace()) && visitedImports.get(importLine.getNamespace()) == null){
                throw new ParseError(0,0, "Cyclic import detected! " + "The import of " + importLine.getNamespace() + " in " + featureModel.getNamespace() + " creates a cycle", null);
            }else {
                try {
                    String path = fileLoader.get(importLine.getNamespace());
                    Path filePath = Path.of(path);
                    String content = Files.readString(filePath);
                    FeatureModel subModel = parseFeatureModelWithImports(content, fileLoader, visitedImports);
                    importLine.setFeatureModel(subModel);
                    visitedImports.put(importLine.getNamespace(), importLine);
                    featureModel.getConstraints().addAll(subModel.getOwnConstraints());
                    featureModel.getFeatureMap().putAll(subModel.getFeatureMap());
                } catch (IOException e) {
                    throw new ParseError(0, 0, "Could not resolve import: " + e.getMessage(), e);
                }
            }
        }
        return featureModel;
    }

    private void composeFeatureModelFromImports(FeatureModel featureModel){

    }
}
