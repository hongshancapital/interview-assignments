import React from 'react';
import Carousel, { ICarouselProps } from './components/Carousel';
import iphone from './assets/iphone.png';
import airpods from './assets/airpods.png';
import tablet from './assets/tablet.png';
import './App.css';

export const carouselData: ICarouselProps = {
  data: [
    {
      titles: ['xPhone'],
      des: ['Lots to love. Less to spend.', 'Starting at $399.'],
      imgUrl: iphone,
      background: '#111',
      color: '#fff',
    },
    {
      titles: ['Tablet'],
      des: ['Just the right amount of everything.'],
      imgUrl: tablet,
      background: '#fafafa',
      color: '#000',
    },
    {
      titles: ['Buy a Tablet or xPhone for college.', ' Get arPods.'],
      imgUrl: airpods,
      background: '#f1f1f3',
      color: '#000',
    },
  ],
};

function App() {
  return (
    <div className="App">
      <Carousel {...carouselData} />
    </div>
  );
}

export default App;
