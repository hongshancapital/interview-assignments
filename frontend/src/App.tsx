import React from 'react';
import './App.css';
import Carousel from './components/Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {
  return (
    <div className="App">
      <Carousel>
        <div className='page' style={{ backgroundImage: `url(${iphone})` }}>iPhone</div>
        <div className='page' style={{ backgroundImage: `url(${tablet})` }}>tablet</div>
        <div className='page' style={{ backgroundImage: `url(${airpods})` }}>airpods</div>
      </Carousel>
    </div>
  );
}

export default App;
