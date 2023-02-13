/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  coverageDirectory: "coverage",
  testRegex: "(/tests/.*|(\\.|/)(test|spec))\\.tsx?$",
  setupFilesAfterEnv: ['./jest.setup.redis-mock.js'],
};