import React from 'react';
import './App.css';

import { Carousel } from './component/Carousel/Carousel';
import { IitemProps } from './component/Carousel/Item';
const bannerListData: IitemProps[] = [
  {
    id: 0,
    title: 'xPhone',
    desc: 'Lots to love. Less to spend.\nStarting at $399.',
    img: require('./assets/iphone.png'),
    style: { color: 'white' },
  },
  {
    id: 1,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    img: require('./assets/tablet.png'),
  },
  {
    id: 2,
    title: 'Buy a tablet or xPhone for college.\nGet airpods.',
    img: require('./assets/airpods.png'),
  },
];

function App() {
  return (
    <div className="App">
      <Carousel
        delaySeconds={3}
        itemList={bannerListData}
        onChange={(index: number) => {
          console.log(index);
        }}
      ></Carousel>
    </div>
  );
}

export default App;
