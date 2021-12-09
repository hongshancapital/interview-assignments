import React from "react";
import Carousel from "./components/carousel";
import "./App.css";

import iphonePNG from './assets/iphone.png';
import tabletPNG from './assets/tablet.png';
import airpodsPNG from './assets/airpods.png';

import { ICarouselItemProps } from './components/carousel/type';

const carouselItems: ICarouselItemProps[] = [
  {
    title: ['xPhone'],
    content: ['Lots to love. Less to spend.', 'Starting at $399'],
    image: iphonePNG,
    color: '#fff',
    bgColor: '#111'
  },
  {
    title: ['Tablet'],
    content: ['Just the right amount of everything'],
    image: tabletPNG,
    bgColor: '#fafafa',
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
    image: airpodsPNG,
    bgColor: '#f1f1f3'
  }
]

function App() {
  return (
    <div className="App">
      <Carousel items={carouselItems} style={{height: '100vh'}} />
    </div>
  );
}

export default App;
