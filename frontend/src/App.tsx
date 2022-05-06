import React from 'react';
import Carousel from './carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import './App.scss';

const beforeChange = (from: number, to: number) => {
  console.log('beforeChange', `${from}-${to}`);
};

const afterChange = (current: number) => {
  console.log('afterChange', current);
};

function App() {
  return (
    <Carousel duration={3000} beforeChange={beforeChange} afterChange={afterChange}>
      <div className="carousel-item" style={{ backgroundImage: `url(${iphone})` }}>
        <div className="carousel-item-text" style={{ color: '#fff' }}>
          <h1>xPhone</h1>
          <span>Lots to love. Less to spend.</span>
          <span>Starting at $399.</span>
        </div>
      </div>
      <div className="carousel-item" style={{ backgroundImage: `url(${tablet})` }}>
        <div className="carousel-item-text" style={{ color: '#000' }}>
          <h1>Tablet</h1>
          <span>Just the right amount of everything.</span>
        </div>
      </div>
      <div className="carousel-item" style={{ backgroundImage: `url(${airpods})` }}>
        <div className="carousel-item-text" style={{ color: '#000' }}>
          <h1>Buy a Tablet or xPhone for college.</h1>
          <h1>Get airPods.</h1>
        </div>
      </div>
    </Carousel>
  );
}

export default App;
