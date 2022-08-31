import React from 'react';
import Carousel from './components/Carousel';
import CarouselItem, { CarouselItemProps } from './components/CarouselItem';
import './App.css';

import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {
  const items: CarouselItemProps[] = [
    {
      title: 'xPhone',
      desc: 'lots to love. Less to speed.\nStarting at $399.',
      img: iphone,
      color: '#fff',
    },
    {
      title: 'Tablet',
      desc: 'Just the right amount of everything.',
      img: tablet,
      color: '#000',
    },
    {
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
      img: airpods,
      color: '#000',
    },
  ];

  return (
    <div className="App">
      <Carousel autoplay={true} interval={3000} duration={500}>
        {items.map((item) => (
          <CarouselItem {...item} key={item.title} />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
