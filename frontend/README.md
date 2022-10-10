# 设计思路

从 Demo.mov 和 assets 中的图片中得知：
 
1. 图片素材大小不一，宽为2880px的倍数，可以通过 backgroundSize 来控制图片大小相同
2. loading 状态可以看做进度条展示，可以单独拆分组件
3. Demo.mov 中，每个 Banner 的文案不同，Banner 内容需要自定义，最好不要通过通用的方式来传值显示

需要两个组件，一个Demo

- Progress 组件
```ts
// 进度条组件，可以控制动画的加载和时长
// props
interface IProgress {
    /**
     * 是否激活状态
     * 激活状态才会有进度条动画
     */
    active?: boolean;
    /**
     * 进度条时长 默认为 2000
     */
    duration?: number;
}
```

- Carousel 组件

```ts
// 只控制 Carousel 的轮播效果，不关心 Banner 中的布局和展示，这样自定义的效果会更灵活
interface ICarouselProps {
    /**
     * 子元素
     */
    children: React.ReactNode;
    /**
     * 轮播时间
     */
    duration?: number;
    /**
     * 是否显示进度条
     */
    dots?: boolean;
}
```


- App 例子

1. 通过 `backgroundSize` 来控制图片大小
2. 所有 `Banner` 中的效果都是使用方自定义


## 单元测试

-------------------------|---------|----------|---------|---------|-------------------
File                     | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s
-------------------------|---------|----------|---------|---------|-------------------
All files                |   88.57 |    91.66 |    90.9 |   88.57 |
 src                     |     100 |      100 |     100 |     100 |
  App.tsx                |     100 |      100 |     100 |     100 |
 src/components/Carousel |   84.61 |    88.88 |   85.71 |   84.61 |
  index.tsx              |   84.61 |    88.88 |   85.71 |   84.61 | 83-87
 src/components/Progress |     100 |      100 |     100 |     100 |
  index.tsx              |     100 |      100 |     100 |     100 |
-------------------------|---------|----------|---------|---------|-------------------

Test Suites: 3 passed, 3 total
Tests:       9 passed, 9 total
Snapshots:   3 passed, 3 total


# Email

56496477@qq.com