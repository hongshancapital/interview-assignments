module.exports = {
  extends: ['react-app'],
  rules: {
    // 配置基础的eslint规则，借助eslint fix实现代码的格式化
    'quote-props': ['error','as-needed',{ keywords: false }],
    quotes: ['warn','single',{ allowTemplateLiterals: false }],
    semi: ['error','always'],
    indent: ['warn', 2, {
      ignoredNodes: ['JSXElement :not(JSXExpressionContainer, JSXExpressionContainer *)'],
    }],
    'key-spacing': 'error',
    'no-trailing-spaces': 'error',
    'no-whitespace-before-property': 'warn',
    'object-curly-spacing': ['warn','always'],
    'array-bracket-spacing': ['warn','never'],
  },
};
