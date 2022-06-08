import React from "react";
import Carousel from './Carousel'
import img1 from './assets/iphone.png'
import img2 from './assets/tablet.png'
import img3 from './assets/airpods.png'
import "./App.css";
import { CarouselItem } from './Carousel/types';

const config: CarouselItem[] = [
  {
    textList: ['xPhone'],
    bgColor: '#0E0E0E',
    color: '#fff',
    sub: ['Lots to love. Less to spend.', 'Starting at $399.'],
    img: img1
  },
  {
    textList: ['Tablet'],
    bgColor: '#F9F9F9',
    color: '#000',
    sub: ['Just the right amount of everything.'],
    img: img2
  },
  {
    textList: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
    bgColor: '#F1F1F3',
    color: '#000',
    sub: [],
    img: img3
  },
]

function App() {
  return <div className="App">
    <Carousel
      scrollDirection={"row"}
      initialPage={0}
      config={config}
    />
  </div>;
}

export default App;
