import React from 'react';
import { Carousel } from './components';

import './App.css';

function App() {
  const config = {
    speed: 3,
  };
  return (
    <div className="App">
      <Carousel {...config}>
        <div className="demo bg-1">
          <h3 className="title color-white">xPhone</h3>
          <p className="sub color-white">Lots to love. Less to spend</p>
          <p className="sub color-white">Starting at $399.</p>
        </div>
        <div className="demo bg-2">
          <h3 className="title color-black">Tablet</h3>
          <p className="sub color-black">
            Just the right amount of everything.
          </p>
        </div>
        <section className="demo bg-3">
          <h3 className="title color-black">
            Buy a Tablet or xPhone for college
          </h3>
          <h3 className="title color-black">Get arpods.</h3>
        </section>
        {null}
        {false}
        {/* 'TEST STRING' */}
      </Carousel>
    </div>
  );
}

export default App;
