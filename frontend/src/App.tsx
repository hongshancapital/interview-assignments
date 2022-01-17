import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import CarouselItem from "./components/carousel/item";
import Airpods from "./components/demo/airpods";
import Tablet from "./components/demo/tablet";
import XPhone from "./components/demo/xPhone";

function App() {
  return <div className="App">
    <div className="title">carousel demo</div>
    <Carousel className="demo">
      <CarouselItem>
        <XPhone/>
      </CarouselItem>
      <CarouselItem>
        <Tablet/>
      </CarouselItem>
      <CarouselItem>
        <Airpods/>
      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
