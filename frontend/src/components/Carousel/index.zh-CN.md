
轮播组件

## 何时使用

- 用于一组图片或卡片轮播。

## API
### Carousel

| 参数 | 说明 | 类型 | 默认值 | 
| --- | --- | --- | --- |
| current | 初始化值 | number | 0 |
| easing | 动画效果 'linear' \| 'ease' \| 'ease-in' \| 'ease-out' \| 'ease-in-out' | string | `linear` |
| autoplay | 是否自动切换 | boolean | true |
| onChange | 切换的回调 | function(current) | - |
| dots | 是否显示面板指示点 | boolean | true |
| interval | 切换间隔时间(毫秒) | number | 2000 |

#### 方法

| 名称 | 描述 |
| --- | --- |
| goTo(slideNumber) | 切换到指定面板|
| next() | 切换到下一个面板 |
| prev() | 切换到上一个面板 |

### Carousel.Item

| 参数 | 说明 | 类型 | 默认值 | 
| --- | --- | --- | --- | 
| id | 唯一标识 | number或string | - |
| description | 描述 | string \| React.ReactNode | - |
| title | 标题 | string \| React.ReactNode | - |
| style | 样式 | React.CSSProperties| - | 
| className | 容器类名	 | string| - | 



