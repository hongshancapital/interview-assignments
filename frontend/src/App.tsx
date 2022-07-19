import React from 'react';
import './App.css';
import Carousel, { DataField } from './components/Carousel';
import pic1 from './assets/iphone.png';
import pic2 from './assets/tablet.png';
import pic3 from './assets/airpods.png';

function App() {
  const items: DataField[] = [
    {
      title: 'iPhone',
      subTitle: ['Lots to love. Less to spend', 'Starting at $399'],
      img: pic1,
      theme: 'black',
    },
    {
      title: ['Tablet'],
      subTitle: ['Just the right amount of everything'],
      img: pic2,
    },
    {
      title: ['Buy a Tablet or iPhone for college.', 'Get airPods'],
      img: pic3,
    },
  ];

  return (
    <div className='App'>
      <Carousel
        data={items}
        loop
        duration={5}
        style={{
          minHeight: 400,
          minWidth: 980,
          height: '100vh',
          width: '100vw',
        }}
      />
    </div>
  );
}

export default App;
