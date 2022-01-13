# **Carousel 使用说明**

* Carousel为自适应大小，如要控制Carousel的大小和展示位置，请提供一个容器来包裹Carousel。

* 每一个div节点为一个幻灯片，div里面为你要设置的内容。

### 示例代码

```html
<Carousel duration={3000}>
    <div>...</div>
    <div>...</div>
    ...
</Carousel>
```

### 参数定义

`duration` 为可选；类型：number；单位：毫秒；默认值：3000毫秒