import { expect, test } from 'vitest'
import { FeatureModel } from "./index.js"
import fs from 'fs';
import path from 'path';

test('Create a feature model with example1', () => {
  const fm = new FeatureModel('test/example1.uvl')
  expect(fm.toString().substring(0, 8)).toBe('features')
});

test('Create a feature model with example2', () => {
  const fm = new FeatureModel('test/example2.uvl')
  expect(fm.toString().includes('MapViewer')).toBe(true)
});

test('Create a feature model using string', () => {
  const filePath = path.resolve('test/example2.uvl');
  const text = fs.readFileSync(filePath, 'utf8');
  const fm = new FeatureModel(text)
  expect(fm.toString().includes('MapViewer')).toBe(true)
});
