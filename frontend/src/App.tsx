import React from 'react';
import './App.css';
import Carousel from './components/carousel';

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

function App() {
  return (
    <div className="App">
      <Carousel duration={10000} autoplay>
        <div className="wrapper xphone">
          <div className="text">
            <h2>xPhone</h2>
            <p>Lots to love. Less to spend</p>
            <p>Starting at $399</p>
          </div>
          <div className="pic" style={{ backgroundImage: `url(${iphone})` }}></div>
        </div>
        <div className="wrapper tablet">
          <div className="text">
            <h2>Tablet</h2>
            <p>Just the right amount of everything.</p>
          </div>
          <div className="pic" style={{ backgroundImage: `url(${tablet})` }}></div>
        </div>
        <div className="wrapper airpods">
          <div className="text">
            <h2>Buy a Tablet or xPhone for college</h2>
            <h2>Get arPods.</h2>
          </div>
          <div className="pic" style={{ backgroundImage: `url(${airpods})` }}></div>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
