all: java_parser python_parser js_parser

LIB_FLAG = -lib
LIB_PATH = uvl

ROOT_DIR = $(shell pwd)
PYTHON_OUTPUT_DIR = python/uvl

java_parser:
	cd java && mvn package
	cd java && mvn install

python_parser:
	cd uvl/Python && antlr4 $(LIB_FLAG) $(ROOT_DIR)/$(LIB_PATH) -Dlanguage=Python3 -o $(ROOT_DIR)/$(PYTHON_OUTPUT_DIR) UVLPythonLexer.g4 UVLPythonParser.g4

js_parser:
	mkdir -p js/src/lib
	antlr4 $(LIB_FLAG) $(LIB_PATH) -Dlanguage=JavaScript -o js/src/lib/ uvl/UVLJavaScript.g4
	
python_prepare_package:
	cd python && python3 -m build

clean:
	# Remove generated Java files except CustomLexer.java and Main.java
	find java/src/ -type f ! -name 'UVLCustomLexer.java' ! -name 'Main.java' -delete
	# Remove generated Python files except custom_lexer.py and main.py
	find python/uvl/ -type f ! -name 'UVLCustomLexer.py' ! -name 'main.py' -delete
	# Remove compiled Python files
	find . -name "*.pyc" -exec rm {} \;
	# Remove Python build artifacts
	rm -rf python/build python/dist
	# Clean Java build artifacts
	cd java && mvn clean

test:
	python3 ./python/main.py ./tests/parsing/boolean.uvl
dev:
	pip install antlr4-tools antlr4-python3-runtime setuptools wheel twine build
