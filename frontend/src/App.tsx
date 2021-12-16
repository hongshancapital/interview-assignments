import React from "react";
import Carousel from './Carousel'
import "./App.css";

function App() {
  return <div className="App">
    <Carousel>
      <div className="carousel-item carousel-item-first">
        <h1>xPhone</h1>
        <h2>Lots to love. Less to spend.</h2>
        <h3>Starts at $399.</h3>
      </div>
      <div className="carousel-item carousel-item-second">
        <h1>Tablet</h1>
        <h2>Just the right amount of everything.</h2>
      </div>
      <div className="carousel-item carousel-item-third">
        <h1>Buy a Tablet or xPhone for college.</h1>
        <h1>Get airPods.</h1>
      </div>
    </Carousel>
  </div>;
}

export default App;
