import React from "react";
import Carousel from "./Carousel";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel>
        <div className="carousel-item-one">
          <h1>First slide label</h1>
          <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
        </div>
        <div className="carousel-item-two">
          <h1>Second slide label</h1>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </div>
        <div className="carousel-item-three">
          <h1>Third slide label</h1>
          <p>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
          </p>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
