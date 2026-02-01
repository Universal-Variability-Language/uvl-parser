import { expect, describe, test } from 'vitest';
import { FeatureModel } from "../index.js";
import { glob } from 'glob';
import path from 'path';

const TEST_MODELS_DIR = path.resolve(import.meta.dirname, '../../test_models');

const validFiles = glob.sync(`${TEST_MODELS_DIR}/**/*.uvl`, { ignore: '**/faulty/**' });
const faultyFiles = glob.sync(`${TEST_MODELS_DIR}/faulty/*.uvl`);

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
