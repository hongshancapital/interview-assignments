# Carousel 轮播组件

## 设计思路


**dom布局**

```
div.carousel-container     轮播容器
    div.carousel-transition  轮播动画组件
        div.carousel-item      图文容器
        div.carousel-item      图文容器
        div.carousel-item      图文容器
    div.carousel-navi      导航组件 pre, next
    div.carousel-indicator 进度指示器容器
        div.carousel-indicator-item 进度指示器
        div.carousel-indicator-item 进度指示器
        div.carousel-indicator-item 进度指示器
```

**组件拆分**

- Carousel
- CarouselContainer
- CarouselNavi
- CarouselTransition  
- CarouselItem
- CarouselIndicator

**滑动效果**

利用定时器动态控制内连样式 `transform`,`transition-duration` 实现左右滑动

```ts
const slide = (offsetX: number = 0, offsetY: number = 0, duration: number = 0) => ({
  transform: `translate3d(${offsetX}00%, ${offsetY}00%, 0)`,
  transitionDuration: duration * 1000 + 'ms',
});
```

## 开发规范

- 样式类名遵循`BEM`原则
- 基于react-hooks 实现
- 支持修改 css 类名前辍 
- 组件文件名及组件名称遵循`pascal`命名，例如 `CarouselItem`
- 组件属性尽可能提供默认值

## Carousel 组件属性

|属性名称|类型|默认值|说明|
|:--|:--|:--|:--|
| width | string | `'100%'` | 轮播容器宽度 例如 '100vw'
| height | string | `'100%'` | 轮播容器高度 例如 '100vh'
| animation | string | `'slideLeft'` | 动画效果，可选值：`slideLeft \ slideRight` 
| duration | number | `3000` | 动画间隔时间 单位ms 
| showIndicator | boolean |  `false` | 是否显示进度指示器
| showNavi | boolean | `true` | 是否显示导航控制（pre, next）
| overPause | boolean | `false` | 鼠标悬停时暂停动画

## api

### defineCarouselProps 

> 在组件外部定义属性时, 可以使用类型友好的 defineCarouselProps函数, 支持属性自动完成及属性类型检测。


```tsx
import {Carousel,CarouselItem defineCarouselProps} from './components/Carousel'

const carouselProps = define({width: '100vw', height: '100vh', animation: 'slideLeft'})


<Carousel {...carouselProps}>
  <CarouselItem>1</CarouselItem>
  <CarouselItem>2</CarouselItem>
  <CarouselItem>3</CarouselItem>
</Carousel>

```

## hooks

useMouse 获取鼠标位置的hooks 

useCounter 计数器hooks