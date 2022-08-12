import React, { CSSProperties } from 'react';

import Carousel from './components/carousel';

import './App.css';

const commonStyle: CSSProperties = {
  width: 800,
  height: 600
};

function App() {
  return (
    <div className="App">
      <div style={{ width: 800 }}>
        <Carousel
          beforeChange={(current) => console.log(current)}
          afterChange={(from, to) => console.log(from, to)}
        >
          <div style={{ backgroundColor: 'red', ...commonStyle }}>111111111111111</div>
          <div style={{ backgroundColor: 'blue', ...commonStyle }}>222222222222222</div>
          <div style={{ backgroundColor: 'yellow', ...commonStyle }}>333333333333333</div>
          <div style={{ backgroundColor: 'orange', ...commonStyle }}>44444</div>
        </Carousel>
      </div>
    </div>
  );
}

export default App;
