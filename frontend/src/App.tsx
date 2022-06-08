import React, { CSSProperties } from 'react';
import './App.css';
import Assets from './assets/Assets';
import CarouselHooks from './carousel/CarouselHooks';
import ICarouselItem from './carousel/ICarouselItem';
import MyCarouseContentHooks from './MyCarouseContentHooks';

const data: (ICarouselItem & { style?: CSSProperties })[] = [
  {
    title: 'xPhone',
    des: 'Lots to love. Less to spend.<br/>Starting at $399',
    image: Assets.iphone,
    style: {
      color: 'white',
      backgroundColor: '#111111',
    },
  },
  {
    title: 'Tablet',
    des: 'Just the right amount of everything',
    image: Assets.tablet,
    style: {
      color: '#333',
      backgroundColor: '#fafafa',
    },
  },
  {
    title: 'Buy a Tablet or xPhone for college. <br/>Get arPods',
    image: Assets.airpods,
    style: {
      color: '#333',
      backgroundColor: '#f1f1f3',
    },
  },
];

function App() {
  return (
    <div className="App">
      <CarouselHooks
        interval={3000}
        dataSource={data.map((item) => (
          <MyCarouseContentHooks data={item} style={item.style} />
        ))}
      />
    </div>
  );
}

export default App;
