# Carousel 组件开发及应用
Carousel组件主要核心就是一组卡片在一个公共区域内轮播展示，这其中有几个特点：
- 卡片内容灵活多变，支持文本、图片、文本与图片结合等；卡片数目也不固定
- 支持循环轮播，轮播方向支持自定义，本示例仅支持从右到左水平轮播
- 支持轮播参数配置，包括时间间隔interval、持续时间duration、延迟时间delay等
- 事先定义好轮播数据结构，代码根据该结构生成轮播组件，本例参考：[Options.tsx](./components/Carousel/Options.tsx)
- 提供指示器，指示当前轮播图的位置，要与轮播图滑动同步

## 实现思路
根据上面几点，实现思路就比较明了了，总结如下：
- 首先定义传入的数据结构，然后根据数据确定页面元素框架
- 根据轮播组件不同分工，细化Carousel组成部分，包括Panel、Slider等
- 确定对外提供支持的参数，这些参数决定了Carousel组件所提供的功能
- 确定轮播图切换效果所采用的方案，本文采用transform平移轮播卡片父元素的方式实现轮播，然后设置transform属性的动画，实现轮播效果；同时Slider指示器每个导航元素设置：```transition: width 3s; ```,并且定义一个after伪元素，width初始width为0，在active时，设置width为100%，这样实现了指示器元素动态进度条效果
- 最头疼的一点，指示器与轮播图如何做到同步切换，即上一个指示器进度条拉满后，下一个指示器开始加载时，如何保证卡片切换的下一个。这里主要是通过操作DOM和状态管理实现切换时机一致，每一帧切换时，1）先setIndex当前激活的指示器，开始加载进度条动画，2）然后计算当前偏移量，3）然后代码sleep 3000ms，此时再设置panel-wrap元素的```transform = 'translateX(${offsetX})' ```属性，，开始轮播图切换。这样完成一个轮播过程
- 关于循环播放，播放到最后一个卡片时，重置当前curIndex和offsetX为0，这样就回到原点继续播放

## 代码结构
``` tree
├── assets
├── components
│   ├── Carousel
│   │   ├── components
│   │   │   ├── Panel.tsx
│   │   │   ├── Slider.tsx
│   │   ├── index.css
│   │   ├── index.tsx
│   │   ├── Option.tsx
│   ├── Other
├── pages
│   ├── ProductShow.tsx
├── App.css
├── App.tsx
├── index.css
├── README.md
```
- components：公共组件目录，目前只有Carousel组件，后续可以不断扩展
- pages：页面文件，目前只有demo页面ProductShow
- App.tsx 和 App.css：主文件和主文件对应的样式
- index.tsx 和 index.css：入口文件和全局样式
其中，Carousel组件由Panel、Slider、Option和css等几部分组成，然后对外输出为index.tsx文件

## 亮点介绍
- 实现循环播放过程中，没有使用常规的setInterval，转而使用效率更高的rerequestAnimationFrame，这样相应带来开发难度
- 轮播切换效果没有使用“active状态切换”，而是直接通过transform父元素的方式，这样讲整个动画过程聚合到一个“动画实例”中，而不是分散到不同的”实例“，这样也带来了需要保证轮播切换和指示器进度条动画同步的问题
- 每个轮播图元素的宽度及指示器的宽度都是百分比设置，这样保证了在不同尺寸的兼容适配

## 项目启动
使用命令 ``` yarn start ``` 或 ``` npm run start ```，启动成功后自动打开端口为3000的本地浏览地址：http://localhost:3000/
* 注意：Node版本建议在v15以上，否则启动报错

## 单元测试&打包
- 单元测试：``` yarn test ```
- 打包：``` yarn build ```

## 待改进
- 未完成功能：鼠标移入悬停，不支持手动播放和停止功能
- sleep函数采用setTimeout，在一些情况下并不能完全保证轮播切换效果一致
- 接入gitlab-ci自动化部署流程