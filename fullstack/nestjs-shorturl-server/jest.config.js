/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  moduleNameMapper: {
    '^@/(.*)$': './src/$1'
  },
  collectCoverage: false,
  collectCoverageFrom: [
    'src/**/{app,short-url}.{controller,service}.{ts,js,jsx}',
    '!**/*.d.ts'
  ]
}
