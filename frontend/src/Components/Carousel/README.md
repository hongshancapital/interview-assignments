# 走马灯 - Carousel
---

> **说明**: 一组轮播的区域。

## 何时使用

- 当有一组平级的内容。

- 当内容空间不足时，可以用走马灯的形式进行收纳，进行轮播展现。

- 常用于一组图片或卡片轮播。

## Example

- 图片即完整内容，高度为组件父级高度
  ```
  <Carousel
    source={[imgSrc1, imgSrc2, imgSrc3]}
  />
  ```
- 透明镂空图片，需要指定背景色，图片位置等
  ```
  <Carousel
    source={[{
      bgPath: imgSrc1,
      bgStyle: {
        backgroundColor: white, 
        backgroundPosition: center bottom;
      }
    }]}
  />
  ```
- 自定义内容
  ```
  <Carousel
    source={[{
      content: (
        <OtherComponent/>
      )
    }]}
  />
  ```

## API
---

| 参数 | 说明 | 类型 | 默认值 | 是否非空 |
| ------| ---- | ---- |----- | ----- |
| source | 资源数据，imgagePath或详细配置 | (string &#124; SourceInfo)[] | - | 是 |
| height | 组件高度 | number &#124; "full" | `full` | 否 |
| easing | 滚动曲线 | "linear" &#124; "ease" &#124; "ease-in" &#124; "ease-out" &#124; "ease-in-out" | `ease` | 否 |
| speed | 滚动动画时间(ms) | number | 500 | 否 |
| delay | 滚动间隔时间(ms) | number | 3000 | 否 |

### SourceInfo

| 参数 | 说明 | 类型 | 默认值 | 是否非空 |
| ------| ---- | ---- |----- |----- |
| bgPath | 背景图片路径 | string | - | 否 |
| bgStyle | 背景容器样式 | cssObject | - | 否 |
| content | 内容填充 | React.ReactNode | - | 否 |

