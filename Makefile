all: java_parser python_parser

java_parser:
	antlr4 -Dlanguage=Java -o java/src/main/java grammar/UVL-java.g4
	cd java && mvn compile

python_parser:
	antlr4 -Dlanguage=Python3 -o python/uvlparser grammar/UVL-python.g4
	cd python && python setup.py build

clean:
	# Remove generated Java files except CustomLexer.java and Main.java
	find java/src/main/java/grammar/ -type f ! -name 'UVLCustomLexer.java' ! -name 'Main.java' -delete
	# Remove generated Python files except custom_lexer.py and main.py
	find python/uvlparser/ -type f ! -name 'UVLCustomLexer.py' ! -name 'main.py' -delete
	# Remove compiled Python files
	find . -name "*.pyc" -exec rm {} \;
	# Remove Python build artifacts
	rm -rf python/build python/dist
	# Clean Java build artifacts
	cd java && mvn clean

test:
	python3 ./python/main.py ./tests/parsing/boolean.uvl
dev:
	pip install antlr4-tools antlr4-python3-runtime
