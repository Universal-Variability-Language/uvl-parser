# UVL - Universal Variability Language

**UVL (Universal Variability Language)** is a concise and extensible language for modeling variability in software product lines. It supports multiple programming languages and provides a grammar-based foundation for building tools and parsers.

This repository contains the **ANTLR4 grammar files** for UVL. With these, you can generate parsers for UVL tailored to specific programming languages like Java, JavaScript, and Python.

## ✨ Key Features

- Language-level modularity
- Namespaces and imports
- Feature trees with attributes and cardinalities
- Cross-tree constraints
- Extensible for different target languages

## 📦 Repository Structure

- `uvl/UVLParser.g4` – Base grammar in EBNF form
- `uvl/UVLLexer.g4` – Base lexer grammar for UVL
- `uvl/Java/UVLJava*.g4`, `uvl/Python/UVLPython*.g4`, etc. – Language-specific grammar files
- `java/` – Java-based parser implementation using Maven
- `python/` – Python-based parser implementation
- `javascript/` – JavaScript-based parser implementation
- `tests/` – UVL test cases for validation

UVL uses [ANTLR4](https://www.antlr.org/) as its parser generator.

---

## 💡 Language Overview

Each UVL model may consist of five optional sections:

1. **Language levels**: Enable optional concepts via `include` keyword.
2. **Namespace**: Allows referencing the model from other UVL models.
3. **Imports**: Include other feature models (e.g., `subdir.filename as fn`).
4. **Feature tree**: Hierarchical features with cardinalities, attributes, and group types (`mandatory`, `optional`, `or`, `alternative`).
5. **Cross-tree constraints**: Logical and arithmetic constraints among features.

### 🔍 Example

```uvl
namespace Server

features
  Server {abstract}
    mandatory
      FileSystem
        or
          NTFS
          APFS
          EXT4
      OperatingSystem {abstract}
        alternative
          Windows
          macOS
          Debian
    optional
      Logging {
        default,
        log_level "warn"
      }

constraints
  Windows => NTFS
  macOS => APFS
```

**Explanation:**
- `Server` is an abstract feature.
- It must include a `FileSystem` and an `OperatingSystem`.
- `Logging` is optional and includes an attribute.
- Logical constraints define dependencies between features.

🔗 More examples: https://github.com/Universal-Variability-Language/uvl-models/tree/main/Feature_Models

---


## Usage 
To use UVL in your projects, you can either:
1. **Use the pre-built parsers**
    ### Java Parser
    Include the following dependency in your Maven project:
    ```xml
    <dependency>
        <groupId>io.github.universal-variability-language</groupId>
        <artifactId>uvl-parser</artifactId>
        <version>0.3</version>
    </dependency>
    ```
    ### Python Parser
    Install the package via pip:
    ```bash
    pip install uvlparser
    ```
    ### JavaScript Parser
    Install the package via npm:
    ```bash
    npm install uvl-parser
    ```
2. **Build the parser manually** See the sections below for details.

## ⚙️ Building the Parser manually
### Java Parser
#### Prerequisites

- [ANTLR4](https://www.antlr.org/)
- Java 17+
- [Maven](https://maven.apache.org/)

#### Build Steps

1. Clone the repository:
  ```bash 
   git clone https://github.com/Universal-Variability-Language/uvl-parser
   ```

2. Build the parser:
  ```bash
   cd java
   mvn clean package
  ```

3. Include the generated JAR in your Java project.
---

## 📚 Resources

**UVL Models & Tools**
- https://github.com/Universal-Variability-Language/uvl-models
- https://www.uvlhub.io/

**Tooling Ecosystem**
- https://github.com/FeatureIDE/FeatureIDE
- https://ide.flamapy.org/
- https://github.com/Universal-Variability-Language/uvl-lsp
- https://github.com/SECPS/TraVarT
- https://github.com/AlexCortinas/spl-js-engine

---

## 📖 Citation

If you use UVL in your research, please cite:

```bibtex
@article{UVL2024,
  title     = {UVL: Feature modelling with the Universal Variability Language},
  journal   = {Journal of Systems and Software},
  volume    = {225},
  pages     = {112326},
  year      = {2025},
  issn      = {0164-1212},
  doi       = {https://doi.org/10.1016/j.jss.2024.112326},
  url       = {https://www.sciencedirect.com/science/article/pii/S0164121224003704},
  author    = {David Benavides and Chico Sundermann and Kevin Feichtinger and José A. Galindo and Rick Rabiser and Thomas Thüm},
  keywords  = {Feature model, Software product lines, Variability}
}
```
---

## 📬 Contact & Contributions

Feel free to open issues or pull requests if you have suggestions or improvements. For questions or collaboration inquiries, visit the UVL Website:
https://universal-variability-language.github.io/
