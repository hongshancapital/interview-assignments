# 设计
src/carousel目录

提供了三个组件
1. carousel：一个controlled轮播组件，通过切换active值选择当前item，切换动画通过css实现
2. progressbars：一个uncontrolled进度条组件，配合useTimeout hook提供一个进度条
3. autoCarouselWithProgressbars：按照需求效果给出的一个实现

这样拆分的目的：
```
这个实现不是一个面向广泛需求的通用组件，而是一个易维护易扩展的简单包装

carousel是轮播部分的简单实现，这一个部分主要通过开放实际html元素上的样式属性，允许使用者定制。

progressbars是需求中进度条的简单模拟，作为轮播的控制器，可以和轮播完全独立。

将两者组合，添加一个state即组成autoCarouselWithProgressbars，模拟了需求的要求
```

随手在progressbar上加了一个onClick效果

# 运行
样式使用src/carousel/carousel.css的简单配置，未引入预处理工具，推荐chrome浏览器观看

也未对demo.mov进行细致匹配

图片来自demo.mov的截图

```
yarn install

yarn start
yarn test
```

# TypeScript Frontend Engineer Assignment

## 要求

- 实现 demo.mov 中的效果
- 封装为 `<Carousel>` 组件
- 使用 React Hooks 实现，不能用 Class Component
- 使用 TypeScript 实现

## 加分项

- 单元测试

## 岗位职责

- 根据产品交互稿构建⾼质量企业级 Web 应⽤
- 技术栈: React + SASS
- 在产品迭代中逐步积累技术框架与组件库
- 根据业务需求适时地重构
- 为 Pull Request 提供有效的代码审查建议
- 设计并撰写固实的单元测试与集成测试
- 与后端⼯程师协同交付产品

## 要求

- 三年以上技术相关工作经验
- 能高效并高质量交付产品
- 对业务逻辑有较为深刻的理解
- 加分项
  - 持续更新的技术博客
  - 长期维护的开源项目
  - 流畅阅读英文技术文档
  - 对审美有一定追求
  - 能力突出者可适当放宽年限

## Create React App 信息

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `yarn start`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br />
You will also see any lint errors in the console.

### `yarn test`

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `yarn build`

Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `yarn eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
