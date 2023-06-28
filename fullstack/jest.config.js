/* eslint-env node */

/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  verbose: true,

  projects: [
    {
      displayName: "INTEGRATION",
      testMatch: ["<rootDir>/__test__/**/*.test.ts"],
      preset: "ts-jest",
      testEnvironment: "node",
    },
    {
      displayName: "UNIT",
      testMatch: ["<rootDir>/src/**/*.test.ts"],
      preset: "ts-jest",
      testEnvironment: "node",
    },
  ],
};
