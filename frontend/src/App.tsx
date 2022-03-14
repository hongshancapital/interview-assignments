import Swiper from './Slider/ui'
import React from 'react';
import './App.css';

function App() {
  const imgList = [
    {
      title: 'iphone',
      src: require('./assets/iphone.png'),
      alt: 'images-1',
    },
    {
      title: 'tablet',
      src: require('./assets/tablet.png'),
      alt: 'images-2',
    },
    {
      title: 'airpods',
      src: require('./assets/airpods.png'),
      alt: 'images-3',
    }
  ];
  return (
    <div className="App">
      <Swiper
        sliderList={imgList}
        dots={true}
        speed={1000}
        delay={3000}
      ></Swiper>
    </div>
  );
}

export default App;
