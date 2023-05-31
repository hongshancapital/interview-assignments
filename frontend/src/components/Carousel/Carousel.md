## API

| 参数            | 说明                                             | 类型                      | 默认值 |
| --------------- | ------------------------------------------------ | ------------------------- | ------ |
| autoplay        | 是否自动切换，值为数字时是轮播间隔时间 单位是 ms | boolean                   | false  |
| speed           | 切花轮播时的过渡动画时长 单位是 ms               | number                    | 300    |
| showSwitch      | 是否显示左右切换按钮                             | boolean                   | false  |
| dots            | 是否显示面板指示点                               | boolean                   | true   |
| className       | 轮播组件根元素样式名称                           | string                    |        |
| onChangeCurrent | 切换面板的回调                                   | (current: number) => void |        |

## 方法

| 名称   | 描述           |
| ------ | -------------- |
| next() | 切换到下一面板 |
| prev() | 切换到上一面板 |
