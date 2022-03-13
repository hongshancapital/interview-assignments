import React from 'react';
import './App.css';
import Carousel from './components/carousel';

import Airpods from './assets/airpods.png';
import iphoneImage from './assets/iphone.png';
import Tablet from './assets/tablet.png';

function App() {
  const carouselList = [
    {
      titles: ['xPhone'],
      contents: ['Lots to love. Less to spend.', 'Starting at $399.'],
      fontColor: '#FFF',
      backgroundImage: iphoneImage,
      backgroundColor: '#111',
    },
    {
      titles: ['Tablet'],
      contents: ['Just the right amount of everything.'],
      fontColor: '#111',
      backgroundImage: Tablet,
      backgroundColor: '#fafafa',
    },
    {
      titles: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
      fontColor: '#111',
      backgroundImage: Airpods,
      backgroundColor: '#f2f2f4',
    },
  ];
  const duration = 3000;
  return (
    <div className="App">
      <Carousel items={carouselList} duration={duration}></Carousel>
    </div>
  );
}

export default App;
