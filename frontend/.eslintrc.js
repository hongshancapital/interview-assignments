module.exports = {
  extends: ['eslint-config-airbnb-base', 'plugin:@typescript-eslint/recommended', 'plugin:prettier/recommended'],
  parser: '@typescript-eslint/parser',
  plugins: ['@typescript-eslint'],
  rules: {
    'import/extensions': 'off',
    'no-use-before-define': 'off',
    '@typescript-eslint/no-use-before-define': ['error', { ignoreTypeReferences: true }],
    'no-prototype-builtins': 'off',
    'import/prefer-default-export': 'off',
  },
  settings: {
    'import/resolver': {
      typescript: {},
      node: {
        extensions: ['.js', '.ts', 'tsx'],
        moduleDirectory: ['node_modules', './src'],
      },
    },
  },
  parserOptions: {
    project: './tsconfig.json',
  },
}