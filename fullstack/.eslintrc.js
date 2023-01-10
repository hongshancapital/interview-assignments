module.exports = {
  'parser': '@typescript-eslint/parser',
  'parserOptions': {
    'ecmaVersion': 'latest',
    'sourceType': 'module',
  },
  'extends': [ 'plugin:@typescript-eslint/recommended' ],
  'env': {
    'node': true, // Enabling Node.js global variables
  },
  'rules': {
    'comma-dangle': [ 2, 'always-multiline' ],
    semi: [ 2, 'never' ],
    quotes: [ 2, 'single' ],
    indent: [ 2, 2 ],
    'comma-spacing': 2,
    'arrow-spacing': 2,
    'key-spacing': [ 2, {beforeColon: false} ],
    'keyword-spacing': [ 2, {before: true} ],
    'space-before-blocks': 2,
    'space-in-parens': 2,
    'space-unary-ops': 2,
    'space-infix-ops': 2,
    'spaced-comment': 2,
    'space-before-function-paren': [ 'error', {
      'anonymous': 'always',
      'named': 'never',
      'asyncArrow': 'always',
    } ],
    '@typescript-eslint/type-annotation-spacing': 2,
    'eol-last': 2,
    'array-bracket-spacing': [ 2, 'always' ],
    'object-curly-spacing': [ 2, 'always' ],
  },
}
