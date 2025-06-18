.PHONY: all java_parser python_parser js_parser python_prepare_package build_python clean test dev

all: java_parser python_parser js_parser

LIB_FLAG = -lib
LIB_PATH = uvl

ROOT_DIR = $(shell pwd)
PYTHON_OUTPUT_DIR = python/uvl
JAVASCRIPT_OUTPUT_DIR = js/src/lib

java_parser:
	cd java && mvn package
	cd java && mvn install

js_parser:
	mkdir -p $(JAVASCRIPT_OUTPUT_DIR)
	cd uvl/JavaScript && \
		antlr4 $(LIB_FLAG) $(ROOT_DIR)/$(LIB_PATH) -Dlanguage=JavaScript -o $(ROOT_DIR)/$(JAVASCRIPT_OUTPUT_DIR) UVLJavaScriptLexer.g4 UVLJavaScriptParser.g4	

python_parser:
	cd uvl/Python && \
		antlr4 $(LIB_FLAG) $(ROOT_DIR)/$(LIB_PATH) -Dlanguage=Python3 -o $(ROOT_DIR)/$(PYTHON_OUTPUT_DIR) UVLPythonLexer.g4 UVLPythonParser.g4

python_prepare_package:
	cd python && python3 -m build

clean:
	# Clean Java build artifacts
	cd java && mvn clean
	
	# Remove generated Python files except custom_lexer.py and main.py
	find python/uvl/ -type f ! -name 'UVLCustomLexer.py' ! -name 'main.py' -delete
	# Remove compiled Python files
	find . -name "*.pyc" -exec rm {} \;
	# Remove Python build artifacts
	rm -rf python/build python/dist

	# Clean JavaScript generated files
	rm -rf js/src/lib


test:
	python3 ./python/main.py ./tests/parsing/boolean.uvl
dev:
	pip install antlr4-tools antlr4-python3-runtime setuptools wheel twine build
