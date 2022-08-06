# Carousel

Carousel 组件通常用来展示一些重要信息或由图片、视频等组成的内容。

## 初始化

1. 使用 stage cli 进行组件模板初始化，相关内容如下：

- stage: https://www.npmjs.com/package/@fujia/cli-core
- react 组件模板: https://www.npmjs.com/package/@fujia/react-component-template

2. 一些冗余文件是由模板自动生成的，关于「组件文档」的，暂时保留。

## 开发

该组件内容由 https://github.com/ui-puzzles/rect/tree/main/src/components/Carousel(个人开发的一套组件库) 进行二次开发。

### git 规范

1. commit 规范

- husky
- @commitlint/cli 和@commitlint/config-conventional

根据上面三个库，对 commit 规范进行标准化。

### 代码规范

1. 使用函数式组件

2. 其它工程配置参考：https://github.com/ui-puzzles/rect

### 文档规范

1. 使用 storybook 编写组件说明文档和组件演示

- storybook - https://storybook.js.org/

## 测试

1. 单元测试相关库，即 react 自带的

- jest
- @testing-library/react

## 参考资料

1. stage cli - https://www.npmjs.com/package/@fujia/cli-core

2. 组件模板 - https://www.npmjs.com/package/@fujia/react-component-template

3. 开源组件库 - https://github.com/ui-puzzles/rect

4. storybook - https://storybook.js.org/
