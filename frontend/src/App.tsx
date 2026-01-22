import './App.css';
import Carousel from './components/Carousel';
const bannerDataList = [
  {
    id: '1',
    src: require('./assets/airpods.png'),
    descript: 'airpods大降价了，快来买啊',
    color: '#333'
  },
  {
    id: '2',
    src: require('./assets/iphone.png'),
    descript: 'iphone跳楼价大甩卖，快来买啊',
    color: '#fff'
  },
  {
    id: '3',
    src: require('./assets/tablet.png'),
    descript: '忽悠，接着忽悠，你撞猪上了吧',
    color: '#333'
  }
]
function App() {
  return <div className='App'>
    <Carousel bannerDataList={bannerDataList} autoplay/>
  </div>;
}

export default App;
