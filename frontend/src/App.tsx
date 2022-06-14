import React from "react";
import Carousel from "./components/Carousel";
import CarouselItem from "./components/CarouselItem";
import "./App.css";

function App() {
  return <div className="App">
    <Carousel>
      <CarouselItem>
        <div className="item-root blue"></div>
      </CarouselItem>
      <CarouselItem>
        <div className="item-root yellow"></div>
      </CarouselItem>
      <CarouselItem>
        <div className="item-root green"></div>
      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
