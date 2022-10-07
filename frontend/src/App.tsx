import React from "react";
import "./App.css";
import Carousel from './Carousel/index';
import { CarouselItem } from './Carousel/types';
import imgPhone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';
import imgAirpods from './assets/airpods.png';
import './App.css';
const CAROUSEL_DEFAULT: CarouselItem[] = [
  {
    key: 'phone',
    title: 'xPhone',
    description: 'Lots to love. Less to spend.\nStarting at $399.',
    illustration: imgPhone,
  },
  {
    key: 'tablet',
    title: 'Tablet',
    description: 'Just the right amount of everything.',
    illustration: imgTablet,
  },
  {
    key: 'airpods',
    title: 'Buy a Tablet or xPhone for college.\nGet airPods.',
    illustration: imgAirpods,
  },
];
function App() {
  return (
    <div className="App">
      <Carousel dataSource={CAROUSEL_DEFAULT} defaultKey="phone" />
    </div>
  );
}

export default App;
