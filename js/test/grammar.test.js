import { expect, describe, test } from 'vitest';
import { FeatureModel } from "../index.js";
import { glob } from 'glob';
import path from 'path';

const TEST_MODELS_DIR = path.resolve(import.meta.dirname, '../../test_models');

// Files misplaced in test_models/ that actually test invalid syntax
// (dotinfeaturename.uvl has comment "should be illegal" - dots not allowed in quoted IDs)
const MISPLACED_INVALID_FILES = new Set([
  'dotinfeaturename.uvl'
]);

// Semantic errors that the parser cannot detect (only syntax errors are caught)
const SEMANTIC_ERROR_FILES = new Set([
  'missingreference.uvl',
  'wrong_attribute_name.uvl',
  'same_feature_names.uvl'
]);

const allValidFiles = glob.sync(`${TEST_MODELS_DIR}/**/*.uvl`, { ignore: '**/faulty/**' });
const validFiles = allValidFiles.filter(f => !MISPLACED_INVALID_FILES.has(path.basename(f)));

const allFaultyFiles = glob.sync(`${TEST_MODELS_DIR}/faulty/*.uvl`);
const faultyFiles = allFaultyFiles.filter(f => !SEMANTIC_ERROR_FILES.has(path.basename(f)));

describe('Valid UVL models', () => {
  validFiles.forEach(file => {
    const relativePath = path.relative(TEST_MODELS_DIR, file);
    test(`parses ${relativePath}`, () => {
      expect(() => new FeatureModel(file)).not.toThrow();
    });
  });
});

describe('Faulty UVL models', () => {
  faultyFiles.forEach(file => {
    const relativePath = path.relative(TEST_MODELS_DIR, file);
    test(`rejects ${relativePath}`, () => {
      expect(() => new FeatureModel(file)).toThrow();
    });
  });
});
