
import UVLJavaScriptCustomLexer from './UVLJavaScriptCustomLexer.js';
import UVLJavaScriptParser from './lib/UVLJavaScriptParser.js';
import ErrorListener from "./errors/ErrorListener.js";
import antlr4 from 'antlr4';
import fs from 'fs';

export default class FeatureModel {

  constructor(param) {
    this.featureModel = '';
    let chars = '';
    if (this.isFile(param)) {
      chars = new antlr4.FileStream(param);
    } else {
      chars = antlr4.CharStreams.fromString(param);
    }
    this.getTree(chars);
  }

  isFile(str) {
    try {
      return fs.statSync(str);
    } catch (e) {
      console.error('Error: ' + e);
      return false;
    }
  }

  getTree(chars) {
    const lexer = new UVLJavaScriptCustomLexer(chars);
    const tokens = new antlr4.CommonTokenStream(lexer);
    const errorListener = new ErrorListener();
    let parser = new UVLJavaScriptParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(errorListener);
    const tree = parser.featureModel();
    this.featureModel = tree;
  }

  getFeatureModel() {
    return this.featureModel;
  }

  toString() {
    return this.featureModel.getText();
  }
}



