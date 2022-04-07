import React from 'react';
import './App.css';
import Carousel from './components/carousel';
import aripods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import GoodsCard from './components/goods-card';

const demoData = [
  {
    src: iphone,
    title: 'xPhone',
    desc: 'Lots to love.Less to spend.\nStarting at $399',
    color: '#ffffff'
  },
  {
    src: tablet,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    theme: 'light',
  },
  {
    src: aripods,
    title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
    theme: 'light',
  },
];

function App() {
  return (
    <div className='App'>
      <div className='carousel-contain'>
        <Carousel className='carousel-demo'>
          {demoData.map((item, index) => {
            return (
              <GoodsCard
                key={index}
                title={item.title}
                desc={item.desc}
                goodsImg={item.src}
                style={{ color: item.color }}
              />
            );
          })}
        </Carousel>
      </div>
    </div>
  );
}

export default App;
