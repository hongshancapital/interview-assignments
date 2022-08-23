import React from "react";
import "./App.css";
import { Carousel, CarouselItem } from "./Carousel";

function App() {
  return (
    <div className="App">
      <Carousel>
        <CarouselItem>123</CarouselItem>
        <CarouselItem>321</CarouselItem>
        <div>1111</div>
      </Carousel>
    </div>
  );
}

export default App;
