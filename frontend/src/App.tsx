import React from 'react';
import Carousel from './components/Carousel';
import './App.css';

function App() {
  return <div className='App'>
    <Carousel>
      <div className='carousel-box' style={{ backgroundColor: 'beige' }}>1</div>
      <div className='carousel-box' style={{ backgroundColor: 'burlywood' }}>2</div>
      <div className='carousel-box' style={{ backgroundColor: 'cornflowerblue' }}>3</div>
    </Carousel>
  </div>;
}

export default App;
