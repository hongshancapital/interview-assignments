import React from "react";
import Carousel from './carousel';
import type { CarouselItemProps } from './carousel';
import "./App.css";
import imgIphone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';
import imgAirpods from './assets/airpods.png';

const items: CarouselItemProps[] = [{
  className: 'item-iphone',
  titles: ['xPhone'],
  describes: ['Lots to love. Less to spend.', 'Starting at $399.'],
  titleClassName: 'title',
  describeClassName: 'text',
  img: imgIphone,
}, {
  className: 'item-tablet',
  titles: ['Tablet'],
  describes: ['Just the right amount of everything.'],
  titleClassName: 'title',
  describeClassName: 'text',
  img: imgTablet,
}, {
  className: 'item-airpods',
  titles: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
  titleClassName: 'title',
  img: imgAirpods,
}];

function App() {
  return (
    <div className="App">
      <Carousel items={items} interval={3000} animationSpeed={500} />
    </div>
  );
}

export default App;
