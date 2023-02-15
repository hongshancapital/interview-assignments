/** @type {import('ts-jest').JestConfigWithTsJest} */
const path = require("path");

module.exports = {
  rootDir: path.resolve(__dirname, "../"),
  preset: ["ts-jest", "@shelf/jest-mongodb"],
  globals: {
    "ts-jest": {
      babelConfig: true,
      tsConfig: "<rootDir>/tests/tsconfig.test.json",
      diagnostics: false,
    },
    __MONGO_URI__: "mongodb://localhost:27017/jest_unittest",
  },
  moduleFileExtensions: ["ts", "tsx", "json", "node"],
  collectCoverage: true,
  coverageDirectory: "<rootDir>/tests/unit/coverage",
  collectCoverageFrom: [
    "src/server/**/*.ts",
    "!**/*.d.ts",
    "!src/client/**/*.ts",
    "!serverbuild/**/*.js",
  ],
  testEnvironment: "node",
  transform: {
    "^.+\\.(ts|tsx)$": "ts-jest",
  },
};
