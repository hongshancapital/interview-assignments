import React from "react";
import "./App.css";
import Carousel, { Slider } from "./components/carousel";
import { CAROUSEL_CONFIG_DATA } from "./consts";

function App() {
  return <div className="App">
    <Carousel className="my-carousel-warp">
      {CAROUSEL_CONFIG_DATA.map((data, index) => <Slider key={index} {...data} />)}
    </Carousel>
  </div>;
}

export default App;
