# UVL - Universal Variability Language
This is a small default library used to parse and print the Universal Variability Language (UVL).

Under the hood it uses [ANTLR4](https://www.antlr.org/) as the parsing library.
The grammar in EBNF form is located in `src/main/antlr4/de/vill/UVL.g4`

## The Language

On a high level, each feature model in UVL consists of five optional separated elements:

1. **A list of used language levels**
The model can use different concepts which are part of language levels. These levels can either be enumerated with the `include` keyword or be implicit.
2. **A namespace which can be used for references in other models**
3. **A list of imports that can be used to reference external feature models**
The models are referenced by their file name and can be given an alias using a Java import like syntax.
External models in subdirectories can be referenced like this: subdir.filename as fn
4. **The tree hierarchy consisting of: features, group types, and attributes whose relations are specified using nesting (indentation)**
Groups may have an arbitrary number of features as child nodes. A feature can also have a feature cardinality.
Attributes consist of a key-value pair whose key is always a string and its value may be a boolean, number, string, a list attributes, a vector, or a constraint. If the value is a constraint the key must be `constraint`. If the value is a list of constraints the key must be `constraints`
5. **Cross-tree constraints**
Cross-tree constraints may be arbitrary propositional formulas with the following symbols: => (implies), <=> (iff), & (and), | (or), ! (not), or brackets. Through the usage of language levels cross-tree constraints can also contain equations (<,>,==) which consist of expressions (+,-,*,/) with numbers or numerical feature attributes as literals and aggregate functions (avg, sum).

The following snippet shows a simplified server architecture in UVL. We provide more examples (e.g., to show the composition mechanism) in [https://github.com/Universal-Variability-Language/uvl-models/tree/main/Feature_Models](https://github.com/Universal-Variability-Language/uvl-models/tree/main/Feature_Models).

```
namespace Server

features
  Server {abstract}
    mandatory
      FileSystem
        or // with cardinality: [1..*]
          NTFS
          APFS
          EXT4
      OperatingSystem {abstract}
        alternative
          Windows
          macOS
          Debian
    optional
      Logging	{
      default,
      log_level "warn" // Feature Attribute
    }

constraints
  Windows => NTFS
  macOS => APFS
```

In this snippet, we can recognize the following elements:
* The feature `Server` is abstract (i.e., corresponds to no implementation artifact.
* Each `Server` requires a `FileSystem`and an `OperatingSystem` denoted by the *mandatory* group
* The `Server` may have `Logging` denoted by the *optional* group
* A `FileSystem` requires at least one type of `NTFS`, `APFS`, and `Ext4` denoted by the *or* group
* An `OperatingSystem` has exactly one type of `Windows`, `macOS`, and `Debian`denoted by the *alternative* group
* `Logging` has the feature attribute `log_level` attached which is set to "warn"
* `Windows` requires `NTFS` denoted by the first cross-tree constraint
* `macOS`requires `APFS`

## Building a jar

The library is a maven project and can therefore be build with maven. To update the generated parser classes and create a jar with all necessary dependencies, use:
```
mvn clean compile assembly:single
```

The `target/uvl-parser-1.0-SNAPSHOT-jar-with-dependencies.jar` includes all dependencies.

## Usage from Java
The class `de.vill.main.UVLModelFactory` exposes the static method `parse(String)` which will return an instance of a `de.vill.model.FeatureModel` class. If there is something wrong, a `de.vill.exception.ParseError` is thrown. The parser tries to parse the whole model, even if there are errors. If there are multiple errors, a `de.vill.exception.ParseErrorList` is returned which contains all errors that occurred.
A model can be printed with the `toString()` method of the `de.vill.model.FeatureModel` object.
The following snippet shows a minimal example to read and write UVL models using the jar. More usage examples that also show how to use the acquired UVLModel object can be found in [src/main/java/de/vill/main/Example.java](https://github.com/Universal-Variability-Language/uvl-parser2.0/blob/main/src/main/java/de/vill/main/Example.java)

```Java
// Read
Path filePath = Paths.get(pathAsString);
String content = new String(Files.readAllBytes(filePath));
UVLModelFactory uvlModelFactory = new UVLModelFactory();
FeatureModel featureModel = uvlModelFactory.parse(content);


// Write
String uvlModel = featureModel.toString();
Path filePath = Paths.get(featureModel.getNamespace() + ".uvl");
Files.write(filePath, uvlModel.getBytes());
``` 

## Links
UVL models:
* https://github.com/Universal-Variability-Language/uvl-models

Other parsers:
* https://github.com/Universal-Variability-Language/uvl-parser *deprecated, Initial UVL Parser, based on Clojure and instaparse* **UVL-Parser**
* https://github.com/diverso-lab/uvl-diverso/ *Under development, Antlr4 Parser* **Diverso Lab**

Usage of UVL:
* https://github.com/FeatureIDE/FeatureIDE *Feature modelling tool* 


