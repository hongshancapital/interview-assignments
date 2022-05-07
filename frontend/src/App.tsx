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
    <Carousel
      duration={3000}
      width={window.document.body.clientWidth}
      height={window.innerHeight}
      beforeChange={beforeChange}
      afterChange={afterChange}
    >
      <div
        className="carousel-item"
        style={{
          backgroundColor: '#111',
          color: '#fff',
        }}
      >
        <div className="carousel-item-text">
          <h1>xPhone</h1>
          <p>Lots to love. Less to spend.</p>
          <p>Starting at $399.</p>
        </div>
        <img src={iphone} alt="iphone" />
      </div>
      <div
        className="carousel-item"
        style={{
          backgroundColor: '#fafafa',
          color: '#000',
        }}
      >
        <div className="carousel-item-text">
          <h1>Tablet</h1>
          <p>Just the right amount of everything.</p>
        </div>
        <img src={tablet} alt="tablet" />
      </div>
      <div
        className="carousel-item"
        style={{
          backgroundColor: '#f1f1f3',
          color: '#000',
        }}
      >
        <div className="carousel-item-text">
          <h1>Buy a Tablet or xPhone for college.</h1>
          <h1>Get airPods.</h1>
        </div>
        <img src={airpods} alt="airpods" />
      </div>
    </Carousel>
  );
}

export default App;
