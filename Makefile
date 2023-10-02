.ONESHELL:

.PHONY: build
build:
	antlr4 -Dlanguage=Python3 uvlparser/UVL.g4
test:
	python3 ./uvlparser/main.py ./tests/pizzas.uvl
dev:
	pip install antlr4-tools
	pip install -r requirements.txt