const base = require('../jest.config');
module.exports = {
  ...base,
  rootDir: '..',
  testMatch: [`**/test/unit/**/*.{test,spec}.ts`],
  coverageDirectory: './coverage/unit'
};