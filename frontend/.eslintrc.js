module.exports = {
  extends: ['react-app', 'react-app/jest', 'prettier', 'plugin:storybook/recommended'],
  rules: {
    'max-len': [1, 200],
    'prefer-const': 'error',
    eqeqeq: 'error',
    'simple-import-sort/imports': 'error',
    'simple-import-sort/exports': 'error',
    '@typescript-eslint/no-unused-vars': ['error'],
  },
  plugins: ['simple-import-sort'],
  overrides: [
    {
      files: ['**/*.stories.*'],
      rules: {
        'import/no-anonymous-default-export': 'off',
      },
    },
  ],
};
