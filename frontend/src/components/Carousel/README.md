## API
| 参数 | 说明 | 类型                        | 默认值  |
| --- | --- |---------------------------|------|
| autoplay | 是否自动切换 | boolean                   | true | |     |
| onChange | 轮播切换的回调函数 | Function(index: Number) => void | --   | |     |
| activeIndex | 	跳转到指定的轮播图（受控） | Number                    | --   | |     |
| defaultIndex | 		初始被激活的轮播图 | Number                    | 0    | |     |
| speed | 			轮播速度 |Number                           | 3000 | |     |


## demo
```javascript
import Carousel from "./components/Carousel";

function Demo() {
    return (
        <Carousel>
            <div className="demo">
                1
            </div> 
            <div className="demo">
                2
            </div> 
            <div className="demo">
                3
            </div>
        </Carousel>
    )
}

```