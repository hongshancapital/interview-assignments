/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  collectCoverage: true,
  coverageDirectory: "tests/coverage",
  coverageProvider: "v8",
  transform: {
    '^.+\\.(ts|tsx)?$': 'ts-jest',
  },
};