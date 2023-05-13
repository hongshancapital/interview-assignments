import { MutableRefObject, useRef } from 'react';
import './App.css';
import Carousel, { CarouseApi } from './components/carousel';
import { getCarouPageConfig } from './carouselConfig';

function App() {
  const CarouselRef = useRef<CarouseApi>()
  const pages = getCarouPageConfig(CarouselRef);
  return <div className='App'>
    <Carousel ref={CarouselRef as MutableRefObject<CarouseApi>} pages={pages}></Carousel>
  </div>;
}

export default App;
