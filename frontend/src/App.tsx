import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import CarouselItem from "./components/carousel/item";

function App() {
  return <div className="App">
    <div className="title">carousel demo</div>
    <Carousel height={450}>
      <CarouselItem className="aaa">
        pageA
      </CarouselItem>
      <CarouselItem className="bbb">
        pageB
      </CarouselItem>
      <CarouselItem className="ccc">
        pageC
      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
