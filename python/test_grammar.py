import pytest
import glob
import os
from antlr4 import CommonTokenStream, InputStream
from antlr4.error.ErrorListener import ErrorListener

from main import get_tree
from uvl.UVLCustomLexer import UVLCustomLexer
from uvl.UVLPythonParser import UVLPythonParser

TEST_MODELS_DIR = os.path.join(os.path.dirname(__file__), "..", "test_models")

# Files that are misplaced in test_models/ but actually test invalid syntax
# (dotinfeaturename.uvl has comment "should be illegal" - dots not allowed in quoted IDs)
MISPLACED_INVALID_FILES = {
    "lexing/dotinfeaturename.uvl",
}

# Semantic errors that the parser cannot detect (only syntax errors are caught)
SEMANTIC_ERROR_FILES = {
    "missingreference.uvl",
    "wrong_attribute_name.uvl",
    "same_feature_names.uvl",
}


def relative_path(file):
    return os.path.relpath(file, TEST_MODELS_DIR)


def get_valid_files():
    all_files = glob.glob(os.path.join(TEST_MODELS_DIR, "**", "*.uvl"), recursive=True)
    valid = [f for f in all_files if os.sep + "faulty" + os.sep not in f]
    return [f for f in valid if relative_path(f) not in MISPLACED_INVALID_FILES]


def get_faulty_syntax_files():
    """Only return files with syntax errors (not semantic errors)."""
    all_faulty = glob.glob(os.path.join(TEST_MODELS_DIR, "faulty", "*.uvl"))
    return [f for f in all_faulty if os.path.basename(f) not in SEMANTIC_ERROR_FILES]


@pytest.mark.parametrize("uvl_file", get_valid_files(), ids=relative_path)
def test_valid_model(uvl_file):
    tree = get_tree(uvl_file)
    assert tree is not None


@pytest.mark.parametrize("uvl_file", get_faulty_syntax_files(), ids=relative_path)
def test_faulty_model(uvl_file):
    with pytest.raises(Exception):
        get_tree(uvl_file)


@pytest.mark.parametrize("depth", [64, 65, 300])
def test_nested_attributes(depth):
    class ErrorCollector(ErrorListener):
        def __init__(self):
            self.errors = []

        def syntaxError(self, recognizer, offendingSymbol, line, column, msg, e):
            self.errors.append(msg)

    if depth == 65:
        model_file = os.path.join(os.path.dirname(__file__), "test_models", "nested_attributes_over_limit.uvl")
        with open(model_file, encoding="utf-8") as source:
            model = source.read()
    else:
        model = "features\n    A " + "{a " * depth + "true" + "}" * depth + "\n"
    lexer = UVLCustomLexer(InputStream(model))
    parser = UVLPythonParser(CommonTokenStream(lexer))
    errors = ErrorCollector()
    parser.removeErrorListeners()
    parser.addErrorListener(errors)
    parser.featureModel()

    if depth <= UVLCustomLexer.MAX_ATTRIBUTE_NESTING:
        assert not errors.errors
    else:
        assert any("attribute nesting exceeds 64 levels" in msg for msg in errors.errors)


@pytest.mark.parametrize("attributes", [
    ", ".join(f"a{i} {{b true}}" for i in range(100)),
    "a '" + "{" * 100 + "'",
])
def test_many_shallow_braces(attributes):
    model = "features\n    A {" + attributes + "}\n"
    parser = UVLPythonParser(CommonTokenStream(UVLCustomLexer(InputStream(model))))
    parser.featureModel()
    assert parser.getNumberOfSyntaxErrors() == 0
