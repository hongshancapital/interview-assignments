module.exports = {
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: 'tsconfig.json',
    tsconfigRootDir: __dirname,
    sourceType: 'module'
  },
  plugins: ['@typescript-eslint/eslint-plugin', 'jsdoc', 'import'],
  extends: [
    'plugin:@typescript-eslint/recommended',
    'plugin:prettier/recommended'
  ],
  root: true,
  env: {
    node: true,
    jest: true
  },
  ignorePatterns: ['.eslintrc.js'],
  rules: {
    '@typescript-eslint/interface-name-prefix': 'off',
    '@typescript-eslint/explicit-function-return-type': 'off',
    '@typescript-eslint/explicit-module-boundary-types': 'off',
    '@typescript-eslint/no-explicit-any': 'off',
    '@typescript-eslint/array-type': [
      'error',
      {
        default: 'array-simple'
      }
    ],
    '@typescript-eslint/ban-types': [
      'off',
      {
        types: {
          String: {
            message: 'Use string instead.'
          },
          Number: {
            message: 'Use number instead.'
          },
          Boolean: {
            message: 'Use boolean instead.'
          },
          Function: {
            message: 'Use specific callable interface instead.'
          }
        }
      }
    ],
    '@typescript-eslint/no-this-alias': 'error',
    '@typescript-eslint/member-ordering': 'off',
    'jsdoc/newline-after-description': 1,
    'newline-before-return': 'error',
    'import/no-duplicates': 'error',
    'import/no-unused-modules': 'error',
    'import/no-unassigned-import': 'error',
    'import/order': [
      'error',
      {
        alphabetize: { order: 'asc', caseInsensitive: false },
        'newlines-between': 'always',
        groups: ['external', 'internal', ['parent', 'sibling', 'index']],
        pathGroups: [],
        pathGroupsExcludedImportTypes: []
      }
    ],
    'no-irregular-whitespace': 'error',
    'no-multiple-empty-lines': 'error',
    'no-sparse-arrays': 'error',
    'prefer-object-spread': 'error',
    'prefer-template': 'error',
    'prefer-const': 'error',
    'max-len': 'off',
    'no-console': ['error', { allow: ['warn', 'error'] }]
  }
};
