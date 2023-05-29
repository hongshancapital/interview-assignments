# Carousel组件

## 使用方式
1. 默认使用，可自定义初始index，默认0
```tsx
<Carousel defaultIndex={1} afterChange={(index) => {}}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

2. 定制轮播间隔时间
```tsx
<Carousel gap={3000}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

3. 不开启自动轮播
```tsx
<Carousel autoplay={false}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

4. 结合卡片组件使用
```tsx
<Carousel autoplay={true}>
  {
    list.map((item, index) => <Card {...item} key={`carousel-item-${index}`} />)
  }
  </Carousel>
```

5. 方法使用
```tsx
const carouselRef = useRef<CarouselRefProps>(null);
// 切换到上一卡片
carouselRef.current?.goPrev();
// 切换到下一卡片
carouselRef.current?.goNext();
// 切换指定卡片
carouselRef.current?.goTo(index);

<Carousel ref={carouselRef}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

## Props
| 属性名      | 类型 | 默认值 | 描述 |
| ----------- | ----------- |  --- | --- |
| defaultIndex   | number | 0 | 初始化播放的元素序号 |
| autoplay      | boolean | true | 是否自动轮播 |
| gap   | number | 2000(ms) | 轮播间隔时间 |
| afterChange   | function |  | 轮播切换后的回调 |
| className   | string |  | class名称 |

## 方法
| 名称      | 描述 |
| ----------- | --- |
| goTo(index: number)      | 切换到指定卡片 |
| goNext()      | 切换到下一卡片 |
| goPrev()  | 切换到上一卡片 |