import React from "react";
import Carousel from 'components/Carousel';
import "./App.scss";

function App() {
  return <div className="App">
    <Carousel className='carousel-banner'>
      <div className="carousel-item">
        <h2 className="title">xPhone</h2>
        <p className="sub-title">Lots to love. Less to spend <br />Starting at $399</p>
      </div>
      <div className="carousel-item">
        <h2 className="title">Tablet</h2>
        <p className="sub-title">Just the right amount of everything</p>
      </div>
      <div className="carousel-item">
        <h2 className="title">Buy a Tablet or xPhone for college.<br />Get arPods</h2>
      </div>
    </Carousel>
  </div>;
}

export default App;
