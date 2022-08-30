module.exports = {
  printWidth: 200, //行宽
  semi: true, //分号
  singleQuote: true, // 使用单引号
  useTabs: false, //使用 tab 缩进
  tabWidth: 2, //缩进
  trailingComma: 'es5', //后置逗号，多行对象、数组在最后一行增加逗号
  arrowParens: 'avoid', //箭头函数只有一个参数的时候可以忽略括号
  bracketSpacing: true, //括号内部不要出现空格
  proseWrap: 'preserve', //换行方式 默认值。因为使用了一些折行敏感型的渲染器（如GitHub comment）而按照markdown文本样式进行折行
  parser: 'babylon', //格式化的解析器，默认是babylon
  endOfLine: 'auto', // 结尾是 \n \r \n\r auto
  jsxSingleQuote: false, // 在jsx中使用单引号代替双引号
  jsxBracketSameLine: false, //在jsx中把'>' 是否单独放一行
  stylelintIntegration: false, //不让prettier使用stylelint的代码格式进行校验
  eslintIntegration: false, //不让prettier使用eslint的代码格式进行校验
  tslintIntegration: false, // 不让prettier使用tslint的代码格式进行校验
  htmlWhitespaceSensitivity: 'ignore',
  ignorePath: '.prettierignore', // 不使用prettier格式化的文件填写在项目的.prettierignore文件中
  requireConfig: false, // Require a 'prettierconfig' to format prettier
}
