import React from 'react';
import './App.css';
import Carousel from './components/carousel';
import aripods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import GoodsCard from './components/goods-card';

const demoData = [
  {
    src: aripods,
    name: 'aripods',
    title: 'title',
    desc: 'desc',
    theme: 'light',
  },
  {
    src: iphone,
    name: 'iphone',
    title: 'title',
    desc: 'desc',
    theme: 'dark',
  },
  {
    src: tablet,
    name: 'tablet',
    title: 'title',
    desc: 'desc',
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
              <GoodsCard key={index} title={item.title} desc={item.desc} goodsImg={item.src} />
            );
          })}
        </Carousel>
      </div>
    </div>
  );
}

export default App;
