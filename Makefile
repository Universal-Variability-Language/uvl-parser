.ONESHELL:

.PHONY: build
build:
	antlr4 -Dlanguage=Python3 antlr4-grammar/UVL.g4 -o python/
	mv python/antlr4-grammar/* python
	rm -rf python/antlr4-grammar
test:
	python3 ./python/main.py ./tests/parsing/boolean.uvl
dev:
	pip install antlr4-tools antlr4-python3-runtime
