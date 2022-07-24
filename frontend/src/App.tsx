import React from 'react';
import './App.css';
import Carousel from './components/Carousel';
import XPhone from './pages/xPhone';
import AirPods from './pages/airPods';
import Tablet from './pages/tablet';

function App() {
  return (
    <div className="App" style={{ width: 960, height: 600 }}>
      <Carousel>
        <XPhone></XPhone>
        <Tablet></Tablet>
        <AirPods></AirPods>
      </Carousel>
    </div>
  );
}

export default App;
