## 实现说明

> By: 魏潇—13237157312—前端开发

### Carousel组件实现思路

若将Carousel组件下方进度条视作类似播放器的进度条，进度条播放完毕时，会自动播放下一项，进而循环播放。按照这个思路，大致按照如下目录实现：

````txt
├── Carousel
|   ├── AutoProgress // 进度条组件，可自动播放，播放完触发onEnd事件
|   └── index.tsx // Carousel布局组件，进度条onEnd事件触发后跳转下一项
````

若只实现该功能，进度条播放效果直接用css `transition`属性，播放完毕的事件由绑定的`onTransitionEnd`触发即可，但考虑到后续拓展，姑且希望进度条包含**暂停**、**停止**功能，因此用`requestAnimationFrame`实现进度条计时功能，`AutoProgress`暴露`stoped`、`paused`属性，用于播放条的停止和暂停。

更进一步，Carousel组件可能会拓展许多手势操作，如：hover时暂停播放、拖动翻页等。为了保证拓展性，需要提供一个插件机制，可由用户实现各种定制化的手势操作。

目前组件内置了hover时暂停播放、拖动翻页插件，[App.tsx](https://github.com/goblin-pitcher/interview-assignments-dev-weixiao/blob/master/frontend/src/App.tsx#L32)中，解除相关注释，可启用这些手势操作的内置插件

````tsx
......
// 解除插件相关注释，可获取hover暂停、手动翻页功能
// const { createHoverPausePlugin, createDraggablePlugin } = Carousel.plugins;

function App() {
  const plugins = useMemo(()=>[
    // createHoverPausePlugin(), createDraggablePlugin()
  ], []);
  ......
}
````

### 提交内容说明

提交内容分为一下三部分：

1. chore(frontend): 添加项目基础配置, 提交流程相关内容
   + 项目添加scss支持
   + 为保证文件格式统一，配置了基本的eslint、stylelint和pre-commit时的lint-staged检测
2. feat(frontend): 实现Carousel组件, 完成展示页面
   + Carousel组件的具体实现
3. test(frontend): 添加Carousel组件相关测试用例
   + 相关测试用例文档

<hr />

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
