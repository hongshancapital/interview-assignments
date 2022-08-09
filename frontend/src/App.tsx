import React from 'react';
import Carousel from './Carousel';
import './App.css';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

function App() {
  return (
    <div className="App">
      <Carousel autoplay>
        <div className='item-one'>
          <div className='text-main'>
            <div className='one'>
              xPhone
            </div>
            <div className='two'>
              Lots to love. Less to spend.
            </div>
            <div className='three'>
              Starting at $399.
            </div>
          </div>
          <img className='img' src={iphone} />
        </div>
        <div className='item-two'>
          <div className='text-main'>
            <div className='one'>
              Tablet
            </div>
            <div className='two'>
              Just the right amount of everything
            </div>
          </div>
          <img className='img' src={tablet} />
        </div>
        <div className='item-three'>
          <div className='text-main'>
            <div className='one'>
              Buy a Tablet or xPhone for college.
            </div>
            <div className='one'>
              Get arPods.
            </div>
          </div>
          <img className='img' src={airpods} />
        </div>
      </Carousel>
    </div>
  );
}

export default App;
