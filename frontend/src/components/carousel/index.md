旋转木马，一组轮播的区域。

## API
| 参数 | 说明 | 类型 | 默认值 |
| --- | --- | --- | --- | --- |
| autoplay | 是否自动切换 | boolean | true |
| afterChange | 切换到指定面板 | function(index) | - |
| className |类名——用来自定义样式|string|-|


## 实现思路
拆分为两部分实现：轮播动画播放部分和轮播按钮部分。
轮播动画主要移动父元素，从而实现子元素轮播的效果。
动画通过slider值变化和`translateX(-${(100 * slider) / childLength}%)`实现。
自动播放时，3s后slider+1,监听到slider变化，自动执行后面的动画；手动播放先清除定时器，然后slider赋值为当前点击的slider。
可通过传入className随意修改组件样式。