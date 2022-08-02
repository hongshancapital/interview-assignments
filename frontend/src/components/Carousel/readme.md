# Carousel 简易版旋转木马

```
  作者: @赵丽新
  微信: 15810010936 
  简书: https://www.jianshu.com/u/347eb5212082
```
## 功能列表: 
1. 支持自动播放
2. 支持自定义内容
3. 支持指示器进度动画
4. 支持点击指示器，跳到指定帧
5. 支持REM自适应尺寸

## 适用场景
- PC、H5

### 属性

| 参数           | 说明          | 类型                              | 默认值  |
| -------------- | ------------- |--------------------------------- | ------- |
| delay          | 切换延时  | `number`(ms)                         | `2000` |
| autoPlay       | 自动运行     | `boolean`                         | `true` |
| loop           | 循环         | `boolean`                         | `true` |



### TODOS
1. 支持纵向
2. 支持拖拽
3. 支持不同尺寸容器
4. 支持一些必要事件，例如onclickItem
5. 支持SASS\LESS等CSS语法糖
6. 支持无缝循环模式
7. 支持移动端滑动手势

### 优化TODOS
1. 用requestAnimationFrame代替window.setTimeout提升流畅度
2. 细化Test Case
3. 启用SASS、LESS等实现js与Css共享样式变量，支持皮肤动态定制
