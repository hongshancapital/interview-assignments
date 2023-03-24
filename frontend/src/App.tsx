import { useMemo } from 'react';
import Carousel, { CarouselDataItem } from './components/Carousel';
import AIRPODS from './assets/airpods.png'
import IPHONE from './assets/iphone.png'
import TABLET from './assets/tablet.png'
import './App.css';


function App() {
  const carouselData = useMemo<CarouselDataItem[]>(() => [
    {
      id: 'xPhone',
      banner: IPHONE,
    },
    {
      id: 'tablet',
      banner: TABLET,
    },
    {
      id: 'airpods',
      banner: AIRPODS,
    },
  ], [])

  return <div className='App'><Carousel dataSource={carouselData} time={3000} /></div>;
}

export default App;
