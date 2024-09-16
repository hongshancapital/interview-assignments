module.exports = {
  root: true,
  // 扩展规则
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:prettier/recommended',
    'prettier',
  ],
  parserOptions: {
    ecmaVersion: 12,
    parser: '@typescript-eslint/parser',
    sourceType: 'module',
  },
  // 注册插件
  plugins: ['@typescript-eslint', 'prettier'],
  // 规则 根据自己需要增加
  rules: {
    'no-var': 'error',
    'no-undef': 0,
    '@typescript-eslint/consistent-type-definitions': ['error', 'interface'],
  },
}
