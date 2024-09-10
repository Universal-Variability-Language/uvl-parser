all: java_parser python_parser js_parser

java_parser:
	antlr4 -Dlanguage=Java -o java/src/main/ uvl/UVLJava.g4
	cd java && mvn compile
	cd java && mvn install

python_parser:
	antlr4 -Dlanguage=Python3 -o python uvl/UVLPython.g4
	cp README.md python
	cd python && python setup.py build

js_parser:
	mkdir -p js/src/lib
	antlr4 -Dlanguage=JavaScript -o js/src/lib/ uvl/UVLJavaScript.g4
	
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
