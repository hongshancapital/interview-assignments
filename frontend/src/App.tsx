import './App.scss';
import { useState } from 'react';
import Carousel from './component/swiper/index';
function App() {
  const [data,setData] = useState([
    {
      banner:require('./assets/airpods.png'),
      text:'1234655555555555555555',
      color:'#000'
    },
    {
      banner:require('./assets/iphone.png'),
      text:'kjdfksajdkasjdklkl',
      color:'#fff'
    },
    {
      banner:require('./assets/tablet.png'),
      text:'dfkjsdkfsdfkhjdfhakjhdfsas',
      color:'#000'
    }
  ])
  const [timeObj,setTimeObj]=useState({
    playTime:2000,
    lazyTime:2000,
  })
  return <div className='App'>
    <Carousel data={data} timeObj={timeObj} />
  </div>;
}

export default App;
