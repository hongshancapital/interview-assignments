import React from "react";
import "./App.css";
//--- carousel
import { Carousel, Layer } from './lib/carousel';

function App() {
  return (
    <div className="App">
      <Carousel>
        <Layer layer={0} className='bg'>
          <div slide={0} className='img-iphone bg'></div>
          <div slide={1} className='img-tablet bg'></div>
          <div slide={2} className='img-airpods bg'></div>
        </Layer>
        <Layer layer={1} className='title'>
          <div slide={0}>
            <h4 className="color-w title">xPhone</h4>
            <p className="color-w subtitle">Lots to love. Less to spend. Starting at $399</p>
          </div>
          <div slide={1} className='img-tablet bg'>
            <h4 className="title">Tablet</h4>
            <p className="subtitle">Just the right amount of everything</p>
          </div>
          <div slide={2} className='img-airpods bg'>
            <h4 className="title">Buy a Tablet or xPhone for college Get arPods</h4>
          </div>
        </Layer>
      </Carousel>
    </div>
  );
}

export default App;
