import React from "react";
import "./App.scss";
import Carousel from "./Carousel";

function App() {
  return (
    <div className="app">
      <div className="carousel-container">
        <Carousel speed={3000}>
          <div className="product product-xphone">
            <h4 className="product-name">xPhone</h4>
            <p className="product-intro">
              Lots to love. Less to spend.
              <br /> Starting as $399.
            </p>
          </div>

          <div className="product product-tablet">
            <h4 className="product-name">Tablet</h4>
            <p className="product-intro">
              Just the right amount of everything.
            </p>
          </div>

          <div className="product product-airpods">
            <div className="product-intro">
              Buy a Tablet or xPhone for college. <br />
              Get arPods.
            </div>
          </div>
        </Carousel>
      </div>
    </div>
  );
}

export default App;
