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

2. 禁用轮播
```tsx
<Carousel autoplay={false}>
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
