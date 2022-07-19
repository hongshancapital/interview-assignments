## Carousel

轮播图组件

### API

**属性**

| 参数              | 说明                                                  | 类型                         | 默认值        |
| ----------------- | ---------------------------------------------------- | ---------------------------- | ------------ |
| items             | 轮播图的每一项                                         | Array                        | 无           |
| duration          | 图片切换间隔时间(毫秒)                                  | Number                       | 3000         |

**轮播图的每一项的属性**

| 参数              | 说明                                                  | 类型                         | 默认值        |
| ----------------- | ---------------------------------------------------- | ---------------------------- | -------------|
| title             | 主标题                                                | String                       | 无           |
| subTitle          | 子标题                                                | String                       | 无           |
| pic               | 图片对象                                               | Object                      | 无           |
| className         | 自定义样式                                             | String                       | 无           |

### 示例
```
<script>
const carouselItems = [{
  title: 'xPhone',
  subTitle: 'Lots to love. Less to spend. Starting at $399.',
  pic: require('./assets/iphone.png'),
  className: 'ci-title-wrap-0'
}, {
  title: 'Tablet',
  subTitle: 'Just the right amount of everything.',
  pic: require('./assets/tablet.png')
}, {
  title: 'Buy a Tablet or xPhone for college.',
  subTitle: 'Get arPods.',
  pic: require('./assets/airpods.png'),
  className: 'ci-title-wrap-2'
}]

function App() {
  return <div className="App">
    <Carousel items={carouselItems} duration={3000} speed={400}/>
  </div>;
}
</script>
 
```




