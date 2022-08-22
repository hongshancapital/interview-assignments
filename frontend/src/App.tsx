import React from "react";
import "./App.css";
import { Carousel } from "./Carousel";
import { CarouselItem } from "./CarouselItem";
import { CarouselLists } from "./constants";

function App() {
  return <div className="App">{
    <Carousel>
      {
        CarouselLists.map((item) => {
          return <CarouselItem {...item}></CarouselItem>
        })
      }
    </Carousel>
  }
  </div>;
}

export default App;
