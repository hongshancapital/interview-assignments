## 属性

| 参数         | 说明                                                                                         | 类型                              | 默认值   |
| ------------ | -------------------------------------------------------------------------------------------- | --------------------------------- | -------- |
| autoplay     | 是否自动切换                                                                                 | boolean                           | false    |
| duration     | 单页面停留时长                                                                               | number                            | 3000     |
| dots         | 是否显示面板指示点，如果为 `object` 则同时可以指定 `dotsClass` 或者                          | boolean \| { className?: string } | true     |
| dotPosition  | 面板指示点位置，可选 `top` `bottom` `left` `right`, 像 `left` `right` 可以扩展纵向轮播时使用 | string                            | `bottom` |
| className    | 指定轮播根组件类名                                                                           | string                            | -        |
| afterChange  | 切换面板的回调                                                                               | function(current)                 | -        |
| beforeChange | 切换面板的回调，可以通过返回 `false`，停止进入下一页                                         | function(from, to)                | -        |

## 方法

| 名称                   | 描述           |
| ---------------------- | -------------- |
| goTo(index, animation) | 切换到指定面板 |
| next()                 | 切换到下一面板 |
| prev()                 | 切换到上一面板 |
