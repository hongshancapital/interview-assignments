import React from 'react';
import './App.css';
import { Carousel, CarouselItem } from './carousel/Carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

const carouselItems: Array<CarouselItem> = [
  {
    bgImgUrl: iphone,
    title: 'xPhone',
    text: 'Lots to love. Less to spend.\nStarting at $399.',
    fontColor: '#fff'
  },
  {
    bgImgUrl: tablet,
    title: 'Tablet',
    text: 'Just the right amount of everything.'
  }, {
    bgImgUrl: airpods,
    title: 'Buy a Tablet or xPhone for college.\nGet arPods.'
  }
]

function App() {
  return <div className="App">
    <Carousel items={carouselItems}></Carousel>
  </div>;
}

export default App;
