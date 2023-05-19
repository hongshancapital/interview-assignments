# Carousel组件

## 使用方式
1. 默认使用
```tsx
<Carousel>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

2. 修改轮播时间
```tsx
<Carousel autoplayInterval={5000}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

3. 禁用轮播
```tsx
<Carousel autoplay={false}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

4. 使用方法
```tsx
const carouselRef = useRef<CarouselRefProps>(null);
// 切换到上一面板
// 1. carouselRef.current?.prev();
// 切换到下一面板
// 1. carouselRef.current?.next();
// 切换指定面板
// 1. carouselRef.current?.goTo(1);

<Carousel ref={carouselRef}>
  <div>1</div>
  <div>2</div>
  <div>3</div>
</Carousel>
```

## Props
| 属性名      | 类型 | 默认值 | 描述 |
| ----------- | ----------- |  --- | --- |
| autoplay      | boolean | true | 是否自动轮播 |
| autoplayInterval   | number | 3000(ms) | 轮播延迟时间 |

## 方法
| 名称      | 描述 |
| ----------- | --- |
| goTo(index: number)      | 切换到指定面板 |
| next()      | 切换到下一面板 |
| prev()  | 切换到上一面板 |
