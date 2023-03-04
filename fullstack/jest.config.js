/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  ...require('@shelf/jest-mongodb/jest-preset'),
  ...require('ts-jest/jest-preset'),
  testEnvironment: "node",
  collectCoverage: true,
  setupFiles: ["./src/setup.ts"],
};
