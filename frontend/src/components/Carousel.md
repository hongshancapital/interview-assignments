# Carousel 轮播

### 引入

```javascript
import React from 'react'
import { Carousel, CarouselItem } from './Carousel'
```

## 代码演示

### 基础用法

通过 `autoplay`、`duration` 属性设置自动轮播间隔

```jsx
<Carousel autoplay duration={3000}>
  <CarouselItem>1</CarouselItem>
  <CarouselItem>2</CarouselItem>
  <CarouselItem>3</CarouselItem>
</Carousel>
```

通过 `content` 属性填充轮播图内容

```jsx
<Carousel style={{ height: '100vh' }}>
  <CarouselItem
    content={{
      title: 'xPhone',
      subtitle: 'Lots to Love. Less to spend.\nStarting at $399.',
      color: '#ffffff',
      background: require('./assets/iphone.png'),
      backgroundColor: '#111111'
    }}
  />
  <CarouselItem
    content={{
      title: 'Tablet',
      subtitle: 'Just the right amount of everything.',
      color: '#000000',
      background: require('./assets/tablet.png'),
      backgroundColor: '#fafafa'
    }}
  />
  <CarouselItem
    content={{
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
      color: '#000000',
      background: require('./assets/airpods.png'),
      backgroundColor: '#f1f1f3'
    }}
  />
</Carousel>
```

自定义渲染内容

```jsx
<Carousel className={'my-carousel'}>
  <CarouselItem>
    <img src="https://sf1-ttcdn-tos.pstatp.com/obj/caijing-cashdesk/douyin_wallet_1.png" style={{ width: '100%' }} />
  </CarouselItem>
  <CarouselItem>
    <img src="https://sf1-ttcdn-tos.pstatp.com/obj/caijing-cashdesk/douyin_wallet_2.png" style={{ width: '100%' }} />
  </CarouselItem>
  <CarouselItem>
    <img src="https://sf1-ttcdn-tos.pstatp.com/obj/caijing-cashdesk/douyin_wallet_3.png" style={{ width: '100%' }} />
  </CarouselItem>
</Carousel>
```

### 默认展示页

通过通过 `defaultActiveIndex` 设置默认展示页

```jsx
<Carousel defaultActiveIndex={2}>
  <CarouselItem>1</CarouselItem>
  <CarouselItem>2</CarouselItem>
  <CarouselItem>3</CarouselItem>
</Carousel>
```

### 监听 change 事件

```jsx
<Carousel onChange={currentIndex => console.log('更新当前节点index', currentIndex)}>
  <CarouselItem>1</CarouselItem>
  <CarouselItem>2</CarouselItem>
  <CarouselItem>3</CarouselItem>
</Carousel>
```

## API

### Carousel Props

| 参数               | 说明                | 类型      | 默认值 | 版本 |
| ------------------ | ------------------- | --------- | ------ | ---- |
| autoplay           | 是否自动轮播        | `boolean` | -      | -    |
| duration           | 动画时长，单位为 ms | `number`  | `3000` | -    |
| defaultActiveIndex | 初始位置索引值      | `number`  | `0`    | -    |

### Carousel Events

| 事件名   | 说明                 | 回调参数                   |
| -------- | -------------------- | -------------------------- |
| onChange | 每一页轮播结束后触发 | currentIndex, 当前页的索引 |

### CarouselItem Props

| 参数    | 说明         | 类型                  | 默认值 | 版本 |
| ------- | ------------ | --------------------- | ------ | ---- |
| content | 默认填充内容 | `CarouselPropContent` | -      | -    |

### CarouselItem Events

| 事件名  | 说明       | 回调参数     |
| ------- | ---------- | ------------ |
| onClick | 点击时触发 | event: Event |
