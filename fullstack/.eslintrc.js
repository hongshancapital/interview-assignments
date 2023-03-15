/** @format */

module.exports = {
  parser: '@typescript-eslint/parser',
  extends: ['prettier'],
  plugins: ['@typescript-eslint'],
  env: {
    node: true,
  },
  rules: {
    'spaced-comment': 2,
    'space-before-blocks': 2,
    'space-infix-ops': 2,
    'no-irregular-whitespace': 2,
    'no-trailing-spaces': 2,
  },
};
