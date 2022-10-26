#
- 实现了无缝滚动
- 在幻灯片组件中调用了幻灯片组件，保持对幻灯片组件的资源数据无侵入性，符合单一职责，最少知识等原则
- 实现了拖拽更改幻灯片，鼠标移入区域后停止自动更换，变为手动拖拽更换；移出后启动自动更换
- 象征性的考虑到了幻灯片元素内部自定义结构，作为插槽书写在组件标签内部，更加灵活的自定义，解耦性更好（内部元素并非和.mov完全一致）
- 象征性的考虑到幻灯片滚动的方向，切换时间间隔等主要因素（非完美版）
- 考虑到了组件多次实例化的问题
- 象征性的考虑到了部分ts类型声明的问题，没有全部使用any


编译后演示地址（demo page after build）：
https://dbx142857.gitee.io/react-carousel/

其他个人作品演示集合（other personal FE demo）：
https://www.yuque.com/docs/share/6b9d39a4-ee47-473a-846c-af75df83dbbe?# 《jack.d个人作品集汇总》


个人微信(personal wechat id)
dbx714285

个人电话（tel）
（+86 ）13373849087

英文简历（Elglish resume）
http://dbx142857.gitee.io/business-manage/static-files/Jack.du.pdf







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
