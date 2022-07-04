# Carousel 轮播组件

* 支持自使用容器宽高
* 支持总定义轮播内容
* 支持自动轮播动画时间间隔，默认时间为 4s

```js
import Carousel from './components/Carousel';

function App() {
  const data = [
    {
      title: 'XPhone', // 轮播标题
      descs: [
        'Lots to love. Less to Spend.',
        'Starting at $399.'
      ],
      textColor: '#fff',
      href: '#',
      src: require('./assets/iphone.png'),
    },
    {
      title: 'Tablet',
      descs: [
        'Just the right amount of everything.',
      ],
      textColor: '#000',
      href: '#',
      src: require('./assets/tablet.png'),
    },
    {
      title: 'Buy a Tablet or xPhone for college. Get arPods.',
      textColor: '#000',
      href: '#',
      src: require('./assets/airpods.png'),
    }
  ];
  return (
    <div className="App">
      <div className="full-app">
        <Carousel items={data} />
      </div>
    </div>
  );
}
```