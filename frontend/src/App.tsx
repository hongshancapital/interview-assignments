import React from 'react';
import './App.css';
import Carousel from './components/Carousel';

import iphone from './assets/iphone.png';
import airpods from './assets/airpods.png';
import tablet from './assets/tablet.png';

import './demo.scss';

function App() {
  return (
    <div className='App'>
      <Carousel style={{ width: '100%' }}>
        <div className='demo-item'>
          <div className='demo-item-title light'>
            <h1>xPhone</h1>
            <p className='demo-item-title-sub'>Lots to love.Less to spend.</p>
            <p className='demo-item-title-sub'>Starting at $399.</p>
          </div>
          <div>
            <img src={iphone} alt='iphone' width='100%' />
          </div>
        </div>
        <div className='demo-item light'>
          <div className='demo-item-title'>
            <h1>Tablet</h1>
            <p className='demo-item-title-sub'>
              Just the right amount of everything.
            </p>
          </div>
          <div className='demo-item-image'>
            <img src={tablet} alt='tablet' width='100%' />
          </div>
        </div>
        <div className='demo-item light' style={{ backgroundColor: '#f1f1f1' }}>
          <div className='demo-item-title'>
            <h1>Buy a Tablet or xPhone for college.</h1>
            <h1>Get AirPods.</h1>
          </div>
          <div className='demo-item-image'>
            <img src={airpods} alt='airpods' width='100%' />
          </div>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
