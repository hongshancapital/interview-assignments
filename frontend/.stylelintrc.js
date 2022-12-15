module.exports = {
    extends: [
      'stylelint-config-recess-order',
      'stylelint-config-recommended-scss',
    ],

    rules: {
      /**
       * 关于顺序，规则来源于stylelint-order，未针对scss进行适配，重写后的新顺序如下：
       * 1. css3 变量无疑放在第一位，无论是scss变量还是用户定义的样式属性都会用到
       * 2. scss的变量放在第二，scss的at-rules和样式属性都会用到
       * 3. at-rules应在用户属性前面，at-rules优先级较低，可以被样式属性覆盖，放在后面不太合适
       * 4. 用户定义的属性
       * 5. scss嵌套的样式应放在最后
       */
      'order/order': [
        'custom-properties',
        'dollar-variables',
        'at-rules',
        'declarations',
        'rules',
      ],
      // stylelint-scss 默认规则会导致@import .scss文件报错
      // 对此问题官方推荐scss引入用@use语法，但目前项目脚手架不支持高版本scss，因此该规则默认排除.scss
    //   'scss/at-import-partial-extension': ['always', null, { except: ['scss'] }],
      // 该规则会影响scss @import方法的正常使用
    //   'no-invalid-position-at-import-rule': null,

      'block-opening-brace-newline-after': 'always',
      'block-closing-brace-newline-before': 'always',
    },
  };