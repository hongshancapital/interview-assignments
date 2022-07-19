
import React from 'react';
import './App.css';
import Carousel from './component/Carousel';
import { CarouselItemProps } from './component/interface';

import Airpods from './assets/airpods.png';
import Iphone from './assets/iphone.png';
import Tablet from './assets/tablet.png';



function App() {
  const carouselList: CarouselItemProps[] = [
    {
      src: Airpods,
      title: 'AirPords xxxxxxxxxx AirPords',
      color: '#111',
      background: '#F1F1F3'
    },
    {
      src: Iphone,
      title: 'xPhone xxxxxxxxxxxxxx xPhone',
      color: '#FFF',
      background: '#111'
    },
    {
      src: Tablet,
      title: 'Tablet xxxxxxxxxxxxxx Tablet',
      color: '#111',
      background: '#FAFAFA'
    }
  ];
  var duration = 2500;
  return (
    <div className="App">
      <Carousel carouselList={carouselList} duration={duration} ></Carousel>
    </div>
  );
}

export default App;
