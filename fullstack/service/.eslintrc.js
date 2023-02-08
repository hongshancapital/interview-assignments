/* eslint-disable */
module.exports = {
    root: true,
    parserOptions: {
        ecmaVersion: 11,
        sourceType: 'module',
    },
    env: {
        browser: false,
        es6: true,
        node: true,
        commonjs: true
    },
    parser: '@typescript-eslint/parser',
    extends: ['eslint:recommended', 'plugin:@typescript-eslint/recommended'],
    plugins: ['@typescript-eslint'],
    globals: {
        NodeJS: true,
        Exception: true,
        httpArgument: true,
        KeysOf: true,
        InstanceException: true,
        DomainModel: true
    },
    rules: {
        '@typescript-eslint/no-explicit-any': 2,
        '@typescript-eslint/no-this-alias': [
            'error',
            {
                allowDestructuring: true, // Allow `const { props, state } = this`; false by default
                allowedNames: ['self'] // Allow `const self = this`; `[]` by default
            }
        ],
        '@typescript-eslint/no-unused-vars': [2, { 'vars': 'all', 'args': 'after-used' }],
        '@typescript-eslint/no-redeclare': 2, //禁止重复声明变量
        'indent': [2, 4, { SwitchCase: 1 }], //缩进风格
        'linebreak-style': [0, 'error', 'windows', 'unix'], //换行风格
        'quotes': [2, 'single'], // 引号
        'no-caller': 2, //禁止使用arguments.caller或arguments.callee
        'semi': ['error', 'always'], // 分号结尾
        'no-multiple-empty-lines': [2, { 'max': 2 }], // 最大空行2
        'no-console': 2, //不能使用console
        'no-constant-condition': 2, // 禁止在条件中使用常量表达式 if(true) if(1)
        '@typescript-eslint/no-extra-parens': 2, //禁止非必要的括号
        'no-extra-semi': 2, //禁止多余的冒号
        'no-func-assign': 2, //禁止重复的函数声明
        'no-mixed-spaces-and-tabs': [2, false], //禁止混用tab和空格
        'no-trailing-spaces': 1, //一行结束后面不要有空格
        'camelcase': 2, //强制驼峰法命名
        'comma-dangle': [2, 'never'], //对象字面量项尾不能有逗号
        'consistent-this': [2, 'self'], //this别名
        'no-multi-spaces': 1, //不能用多余的空格
        'no-multi-str': 2, //字符串不能用\换行
        'no-undef': 2, //不能有未定义的变量
        'no-sparse-arrays': 2, //禁止稀疏数组， [1,,2]
        'no-unreachable': 2, //不能有无法执行的代码
        'no-unused-expressions': 2, //禁止无用的表达式 如：err? a = 1 : a = 2;
        'no-unused-vars': [2, { 'vars': 'all', 'args': 'after-used' }], //不能有声明后未被使用的变量或参数
        'no-use-before-define': 2, //未定义前不能使用
        'no-extra-boolean-cast': 2, //禁止不必要的boolean转换 如：!!a
        'no-void': 2, //禁用void操作符
        'no-var': 2, //禁用var，用let和const代替
        'brace-style': [1, '1tbs'], //大括号风格
        'arrow-spacing': 0, //=>的前/后括号
        'comma-spacing': [2, { 'before': false, 'after': true }], //逗号前后的空格
        'comma-style': [2, 'last'], //逗号风格，换行时在行首还是行尾
        'curly': [2, 'all'], //必须使用 if(){} 中的{}
        'default-case': 2, //switch语句最后必须有default
        'dot-notation': [0, { 'allowKeywords': true }], //取对象属性使用[]获取
        'eqeqeq': 2, //必须使用全等
        'generator-star-spacing': 0, //生成器函数*的前后空格
        'init-declarations': 2, //声明时必须赋初值
        'key-spacing': [2, { 'beforeColon': false, 'afterColon': true }], //对象字面量中冒号的前后空格
        'newline-after-var': 2, //变量声明后需要空一行
        'id-match': 0, //命名检测
        'semi-spacing': [0, { 'before': false, 'after': true }], //分号前后空格
        'sort-vars': 0, //变量声明时排序
        'space-before-function-paren': [0, 'always'], //函数定义时括号前面要不要有空格
        'strict': 2, //使用严格模式
        'use-isnan': 2, //禁止比较时使用NaN，只能用isNaN()
        'valid-typeof': 2, //必须使用合法的typeof的值
        'no-useless-escape': 2, //可以进行必要的转义，考虑正则表达式
        'require-atomic-updates': 'off'
    },
}
