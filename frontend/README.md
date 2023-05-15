# 作业说明

- 做了性能优化
- 提供了单元测试
- 利用CSS3的transition实现可控动画效果

## 性能优化

- 原始图片的尺寸巨大,且图片内容存在大量纯色背景。本人通过ps裁切功能剪切下核心图片内容，并通过css背景色做为替代。
- 经观察发现核心图片内容较为简单,用到的颜色范围并不多,通过将png24格式的图输出为png8.进一步压缩图片资源尺寸,加快加载速度及减少带宽消耗.而实际显示差异肉眼几乎不可见.(更优解决方案为原始设计导出为svg)
- 动画效果采用css3的transition实现,动画效果更丝滑流畅.

## 特点

- 分层架构设计,拓展性好,维护成本低
>- 数据与视图分离.轮播图抽象为数据列表.新增轮播图时,仅维护轮播列表即可,无需改动组件内容.可无缝通过服务端接口来实现动态轮播
>- 通过分析轮播图总结抽象图片内容结构,并采用动态样式实现.修改banner内容及结构,只需要修改对应数据即可,无需二次关注样式实现.
>- 底部计时器分层封装,若计时器样式或效果改变,仅需替换最底层indicator组件即可.无需关注上层逻辑.

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
