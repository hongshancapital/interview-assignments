import React from "react";
import "./App.css";
import Carousel from './components/Carousel';
import { CarouselItemProps } from './components/CarouselItem';
import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';
import airpodsImg from './assets/airpods.png';

export const carouselItems: CarouselItemProps[] = [
  {
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\n Starting at $399.',
    style: {
      backgroundImage: `url(${iphoneImg})`,
      backgroundSize: 'contain',
      backgroundColor: '#111111',
      color: '#fff',
    },
  },
  {
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.',
    style: {
      backgroundImage: `url(${tabletImg})`,
    },
  },
  {
    title: 'Buy a tablet or xPhone for college.\n Get airPods',
    subTitle: '',
    style: {
      backgroundImage: `url(${airpodsImg})`,
    },
  },
];

function App() {
  return (
    <div className="App">
      <Carousel items={carouselItems} autoPlay startIndex={2} duration={3000} speed={300} />
    </div>
  );
}

export default App;
