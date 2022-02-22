module.exports = {
  env: {
    commonjs: true,
    es6: true,
    node: true
  },
  extends: [
    'standard',
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended'
  ],
  globals: {
    Atomics: 'readonly',
    SharedArrayBuffer: 'readonly'
  },
  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaVersion: 2018
  },
  plugins: [
    '@typescript-eslint'
  ],
  rules: {
    quotes: [2, 'single'],
    indent: [2, 2],
    semi: [2, 'always'],
    '@typescript-eslint/no-empty-function': 'never'
  }
};
