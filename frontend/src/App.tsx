import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

function App() {
  return (
    <div className="App" data-testid="App">
      <Carousel>
        <Carousel.Item>
          <div className="item xphone">
            <h1 className="title">xPhone</h1>
            <p className="text">Lots to love. Less to spend.</p>
            <p className="text">Starting at $399.</p>
          </div>
        </Carousel.Item>
        <Carousel.Item>
          <div className="item tablet">
            <h1 className="title">Tablet</h1>
            <p className="text">Just the right amount of everything.</p>
          </div>
        </Carousel.Item>
        <Carousel.Item>
          <div className="item arpods">
            <h1 className="title">Buy a Tablet or xPhone for college.</h1>
            <h1 className="title">Get arPods.</h1>
          </div>
        </Carousel.Item>
      </Carousel>
    </div>
  );
}

export default App;
