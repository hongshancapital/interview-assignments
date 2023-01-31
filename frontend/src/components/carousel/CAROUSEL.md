> 轻量的 React Carousel 组件，支持定制化

### Example

![Carousel](../../../showcase.mov)

```jsx
onst defaultStyles: CSSProperties = {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  };
  const items: { name: string; dom: ReactNode }[] = [
    {
      name: 'iphone',
      dom: (
        <>
          <h2 className="title">xPhone</h2>
          <div className="text">Lots to love. Less to spend.</div>
          <div>Starting at $399.</div>
        </>
      ),
    },
    {
      name: 'tablet',
      dom: (
        <>
          <h2 className="title">Tablet</h2>
          <div className="text">Just the right amount of everything.</div>
        </>
      ),
    },
    {
      name: 'airpods',
      dom: (
        <>
          <h2 className="title">Buy a Tablet or xPhone for college.</h2>
          <h2 className="text">Get airPods.</h2>
        </>
      ),
    },
  ];

  return (
    <div className="App">
      <div>learn react</div>
      <Carousel style={{ height: '100vh' }}>
        {items.map((item) => (
          <div key={item.name} className={item.name} style={defaultStyles}>
            {item.dom}
          </div>
        ))}
      </Carousel>
    </div>
  );
```

### Props

| Name                  | Type                                                         | Description                                                                                | Default    |
| :-------------------- | :----------------------------------------------------------- | :----------------------------------------------------------------------------------------- | :--------- |
| adaptiveHeight        | `boolean`                                                    | 如果为`true`, 则会自适应高度.                                                              | `false`    |
| afterSlide            | `(index: number) => void`                                    | 更换 slide 需要调用的钩子.                                                                 | `() => {}` |
| animation             | `'zoom' \| 'fade'`                                           | 支持 `zoom` 和 `fade`                                                                      | `zoom`     |
| autoplay              | `boolean`                                                    | 自动播放模式.                                                                              | `false`    |
| loop                  | `boolean`                                                    | 是否循环.                                                                                  | `false`    |
| autoplayInterval      | `number`                                                     | 间隔`autoplayInterval`ms.                                                                  | `3000`     |
| autoplayReverse       | `boolean`                                                    | slide 由高到低轮播 调换轮播顺序.                                                           | `false`    |
| beforeSlide           | `(currentSlideIndex: number, endSlideIndex: number) => void` | 幻灯片更改前调用                                                                           | `() => {}` |
| cellAlign             | `'left' \| 'center' \| 'right'`                              | 显示多个幻灯片时，设置将当前幻灯片固定到的位置.                                            | `left`     |
| cellSpacing           | `number`                                                     | slide 间的间隔, support as `px`                                                            | `0`        |
| className             | `string`                                                     | frame class name                                                                           | `''`       |
| defaultControlsConfig | 控制配置，详情看`defaultControlsConfig`                      | `{}`                                                                                       |
| disableAnimation      | `boolean`                                                    | 设置为 `true`, 将禁止 animation.                                                           | `false`    |
| easing                | `(normalizedTime: number) => number`                         | 动画模式. 详细了解 [Easing section](#easing-and-edgeeasing)                                | easeOut    |
| edgeEasing            | `(normalizedTime: number) => number`                         | 当滑动到边缘时，触发的动画. 详细了解 [Easing section](#easing-and-edgeeasing)              | easeOut    |
| slideIndex            | `number`                                                     | 手动设置需要显示的 slide 索引                                                              |            |
| slidesToScroll        | `number`                                                     | 滑动一次滚动个数                                                                           | 1          |
| slidesToShow          | `number`                                                     | 一次显示的幻灯片数量                                                                       | 1          |
| speed                 | `number`                                                     | 动画持续时间/过渡速度以毫秒为单位                                                          | `500`      |
| style                 | `CSSProperties`                                              | carousel 行内样式 style                                                                    | `{}`       |
| withoutControls       | `boolean`                                                    | 设置为 `true`, 移除 controls 组件.                                                         | `false`    |
| zoomScale             | `number`                                                     | 添加一个数字值以设置 Animation ===“ Zoom”时设置 Zoom 的比例。 数值应设置在（0,1）的范围内. | `0.85`     |

#### render\*Controls

Type: `(props: ControlProps) => ReactElement`

围绕 `carousel` 的 8 个 渲染位置

- 8 个控制操作渲染位置为 `renderTopLeftControls`, `renderTopCenterControls`, `renderTopRightControls`, `renderCenterLeftControls`, `renderCenterCenterControls`, `renderCenterRightControls`, `renderBottomLeftControls`, `renderBottomCenterControls`, and `renderBottomRightControls`.

- 默认设置 `renderCenterLeftControls` for `Previous` button, `renderCenterRightControls` for the `Next`

- 你能使用 `withoutControls` prop 来移除 `renderControls` on `Carousel`.

Example:

```jsx
<Carousel
  renderTopCenterControls={({ currentSlide }) => (
    <div>Slide: {currentSlide}</div>
  )}
  renderCenterLeftControls={({ previousDisabled, previousSlide }) => (
    <button onClick={previousSlide} disabled={previousDisabled}>
      Previous
    </button>
  )}
  renderCenterRightControls={({ nextDisabled, nextSlide }) => (
    <button onClick={nextSlide} disabled={nextDisabled}>
      Next
    </button>
  )}
>
  {/* Carousel Content */}
</Carousel>
```

- `className` 幻灯片可见度被添加到当前可见的幻灯片或幻灯片中（`SlidestoShow` > 1 时） `className` 幻灯电流添加到当前的“活动”幻灯片中.

#### easing and edgeEasing

`(normalizedTime: number) => number`

支持 0 - 1，你可以自定义 custom easing function.

例如：`(t) => t`，或者可以引入不同的库，像[`d3-ease`](https://github.com/d3/d3-ease).

```jsx
import { easeCircleOut, easeElasticOut } from 'd3-ease';

// ...

<Carousel easing={easeCircleOut} edgeEasing={easeElasticOut}>
  {/* Carousel Content */}
</Carousel>;
```

#### defaultControlsConfig

```
interface DefaultControlsConfig {
  containerClassName?: string;
  nextButtonClassName?: string;
  nextButtonStyle?: CSSProperties;
  nextButtonText?: React.ReactNode;
  prevButtonClassName?: string;
  prevButtonStyle?: CSSProperties;
  prevButtonText?: React.ReactNode;
}
```

使用的默认控件是上一个按钮，下一个按钮。 这些控件的视觉外观和文本可以通过如下所述的道具修改：

- 以 `ClassName` 结尾的道具让您将自定义 `CSS` 类应用于其各自的控制.
- 可以分别使用 `PrevButTontext` 和 `NextButTontext` 自定义上一个按钮和下一个按钮的文本标签.

For example, 你可以更改上一个和下一个按钮的文本，并通过以下配置传递:

```
defaultControlsConfig={{
  nextButtonText: 'Custom Next',
  prevButtonText: 'Custom Prev',
  pagingDotsStyle: {
    fill: 'red'
  }
}}
```

### TODO

- Support paginationDot add onClick
- Support pagination renderControls
- Support touch event
