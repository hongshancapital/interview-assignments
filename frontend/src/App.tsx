import React from 'react';
import Carousel from './Carousel';
import './App.css';

function App() {
    return (
        <div className="App">
          <Carousel>
            <div className="iphone panel">
              <h2>xPhone</h2>
              <p>Lots to love.Less to spend.</p>
              <p>Starting as $399.</p>
            </div>
            <div className="tablet panel">
              <h2>Tablet</h2>
              <p>Just the right amount of everything.</p>
            </div>
            <div className="airpods panel">
              <h2>Buy a Tablet or xPhone for college.</h2>
              <h2>Get arPods.</h2>
            </div>
          </Carousel>
        </div>
    );
}

export default App;
