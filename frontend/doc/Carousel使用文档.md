## Carousel使用文档

引入以下代码迅速实现一个简单的轮播效果

```jsx
<Carousel >
    <div>
    	1
    </div>
    <div>
    	2
    </div>
    <div>
        3
	</div>
</Carousel>
```

自定义分页器的颜色和持续时间

```jsx
<Carousel durning={3000} paginationBackColor="#e4e4e4" paginationColor="white">
    <div>
    	1
    </div>
    <div>
    	2
    </div>
    <div>
        3
	</div>
</Carousel>
```



#### Api

> Carousel Props

| 参数                | 类型                   | 说明                   | 默认     |
| ------------------- | :--------------------- | ---------------------- | -------- |
| children            | Array<React.ReactNode> | 轮播图页面             |          |
| durning             | number                 | 每页轮播图持续时间     | 3000     |
| paginationBackColor | string                 | 轮播图分页器的背景颜色 | \#e4e4e4 |
| paginationColor     | string                 | 轮播图分页器进度条颜色 | white    |

