# UVL Parser Javascript

This is a parser for the UVL (Unified Variability Language) written in Javascript. The parser is based on the ANTLR4 grammar for UVL.

index | content
--- | ---
[Installation](#installation) | How to install the parser
[Usage](#usage) | How to use the parser
[Development](#development) | How to develop the parser
[Publishing in npm](#publishing-in-npm) | How to publish the parser in npm

## Installation

```bash
npm install uvl-parser
```

## Usage

### ES6

```javascript
import { FeatureModel } from 'uvl-parser';
const featureModel = new FeatureModel('file.uvl');
const tree = featureModel.getFeatureModel();
```

## Development

### Install dependencies

```bash
npm install
```

### Build grammar

```bash
npm run build-grammar
```

### Run tests

```bash
npm test
```

## Publishing in npm

The run build will create the folder with the compiled code. To publish in npm, run the following commands:

```bash
npm run build
npm publish --access public 
```

## Authors

- Victor Lamas: [victor.lamas@udc.es](mailto:victor.lamas@udc.es)
- Maria Isabel Limaylla: [maria.limaylla@udc.es](mailto:maria.limaylla@udc.es)
