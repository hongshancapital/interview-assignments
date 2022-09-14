import React from 'react';
import Carousel from './Carousel';
import { CarouselItem } from './Carousel/types';
import imgPhone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';
import imgAirpods from './assets/airpods.png';
// import './App.css';

const CAROUSEL_DATA: CarouselItem[] = [
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
  return <Carousel dataSource={CAROUSEL_DATA} defaultKey="airpods" />;
}

export default App;
