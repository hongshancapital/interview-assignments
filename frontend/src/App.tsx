import React, { useState, useEffect } from 'react';
import './App.css';
import Carousel from './components/carousel/index';
import { CarouselInfo } from './interface/carousel';
import tablet from './assets/tablet.png';
import iphone from './assets/iphone.png';
import airpods from './assets/airpods.png';

const carouselList: CarouselInfo[] = [
  {
    title: ['xPhone'],
    subTitle: ['Lots to love. Less to spend.', 'Starting at $399.'],
    img: iphone,
    color: '#fff',
  },
  {
    title: ['Tablet'],
    subTitle: ['Just the right amount of everything.'],
    img: tablet,
    color: '#000',
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    subTitle: [],
    img: airpods,
    color: '#000',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel carouselList={carouselList} duration={3000} />
    </div>
  );
}

export default App;
