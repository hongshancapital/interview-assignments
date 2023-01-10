const { pathsToModuleNameMapper } = require('ts-jest/utils');
const { compilerOptions } = require('./tsconfig.json');

module.exports = {
  clearMocks: true,
  collectCoverageFrom: [
    '<rootDir>/src/**/*.ts',
    '!<rootDir>/src/base/*.ts',
    '!<rootDir>/src/schemas/*.ts'
  ],
  coverageDirectory: 'coverage',
  coverageReporters: ['text-summary', 'lcov'],
  moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths, {
    prefix: '<rootDir>/src/',
  }),
  testEnvironment: 'node',
  testMatch: ['<rootDir>/src/__tests__/**/*.spec.ts'],
  transform: {
    '.+\\.ts$': 'ts-jest',
  },
  testTimeout: 8000
};
