module.exports = {
  extends: [
    'stylelint-config-standard',
    'stylelint-config-rational-order',
    'stylelint-config-prettier',
  ],
  ignoreFiles: ['**/*.ts', '**/*.tsx'],

  plugins: ['stylelint-declaration-block-no-ignored-properties'],

  customSyntax: require('postcss-sass'),
  rules: {
    'custom-property-pattern': [
      '^([a-z][a-z0-9]*)(-[a-z0-9]+)*$',
      {
        message: 'Expected custom property name to be kebab-case',
      },
    ],
    'function-name-case': ['lower'],
    'function-no-unknown': [true],
  },
};
