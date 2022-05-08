/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  testMatch: ['**/__tests__/**/*.test.ts'],
  testPathIgnorePatterns: ['<rootDir>/node_modules/', '<rootDir>/src/mock','env.ts'],
  transform: {
    '^.+\\.ts$': 'ts-jest',
  },
  globals: {
    'ts-jest': {
      tsconfig: 'tsconfig.json',
    },
  },
  collectCoverageFrom: [
    "src/service/**/*.{j,t}s?(x)",
    "src/controller/**/*.{j,t}s?(x)",
    "src/utils/**/*.{j,t}s?(x)",
    "!<rootDir>/node_modules/",
    "!**/**/*.test.ts"
  ]

};