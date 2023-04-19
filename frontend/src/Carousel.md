### Carousel API

---
#### Props
| 参数                 | 类型                                              | 默认值  | 必填  | 说明               |
|--------------------|-------------------------------------------------|------|-----|------------------|
| slides             | Slide[]                                         | -    | 是   | 幻灯片对象集合          |
| duration           | number                                          | 3000 | 否   | 幻灯片自动切换超时时间      |
| activeId           | number                                          | -    | 否   | 当前显示的幻灯片ID（受控模式） |
| onClickSlideMarker | (id: string &#124; number, idx: number) => void | -    | 否   | 当点击指示器时的回调       |
---
#### Type: Slide
| 属性              | 类型                    | 是否必填 | 描述          |
|-----------------|------------------------|------|-------------|
| id              | string &#124; number   | 否    | 用于受控模式下的唯一标识 |
| textInfos       | TextInfo[]             | 否    | 文本信息集合      |
| backgroundImage | string                 | 是    | 背景图片        |
| extStyles       | Record<string, string> | 否    | 拓展样式 |
---
### Type: TextInfo
| 属性     | 类型                                      | 是否必填 | 默认值     | 描述               |
|--------|-----------------------------------------|------|---------|------------------|
| type   | 'title' &#124;  'desc' &#124;  'custom' | 否    | 'title' | 文本类型 - 标题、正文、自定义 |
| text   | string                                  | 否    | -       | 文本内容，使用自定义渲染时可不传 |
| render | () => React.ReactNode                   | 否    | -       | 自定义渲染函数          |
