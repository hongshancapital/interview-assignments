import React from "react";
import "./App.css";
import { Carousel } from "./components/Carousel";
import { CarouselItem } from "./components/CarouselItem";

function App() {
  return (
    <div className="App">
      <Carousel>
        <CarouselItem className="carousel-iphone">
          <div className="title iphone-title">xPhone</div>
          <div className="text iphone-desc">
            Lots to love. Less to spend.
            <br />
            Starting at $399.
          </div>
        </CarouselItem>
        <CarouselItem
          className="carousel-tablet"
          dotCurrentClassName="carousel-tablet-current-dot"
        >
          <div className="title tablet-title">Tablet</div>
          <div className="text tablet-desc">
            Just the right amount of everything.
          </div>
        </CarouselItem>
        <CarouselItem className="carousel-airpods">
          <div className="title airpods-title">
            Buy a Tablet or xPhone for college.
          </div>
          <div className="text airpods-desc">Get arPods.</div>
        </CarouselItem>
      </Carousel>
    </div>
  );
}

export default App;
