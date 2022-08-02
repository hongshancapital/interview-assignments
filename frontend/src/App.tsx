import React from "react";
import "./App.css";
import { Carousel } from "./component/carousel";
import { CarouselSlide } from "./component/carousel-slide";
import { config } from "./config";

function App() {
  return <div className="App">
    {
      <Carousel duration={3000}>
          {config.map((info, index) => {
            return <CarouselSlide key={index} info={info} />
          })}
      </Carousel>
    }
  </div>;
}

export default App;
