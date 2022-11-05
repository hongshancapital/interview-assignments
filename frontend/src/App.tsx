import React from 'react';

import { Carousel } from './components/index';

import './App.css';

function App() {
  return (
    <div className='App'>
      <div className='content'>
        {
          <Carousel>
            <Carousel.Item key={1}>
              <div className='item iphone'>
                <h5>xPhone</h5>
                <p>Lots to love.Less to spend.</p>
                <p>Strating at $399.</p>
              </div>
            </Carousel.Item>
            <Carousel.Item key={2}>
              <div className='item tablet'>
                <h5>Tablet</h5>
                <p>Just the right amount of everything.</p>
              </div>
            </Carousel.Item>
            <Carousel.Item key={3}>
              <div className='item airpods'>
                <h5>Buy a Tablet of xPhone for college.</h5>
                <h5>Get airPods.</h5>
              </div>
            </Carousel.Item>
          </Carousel>
        }
      </div>
    </div>
  );
}

export default App;
