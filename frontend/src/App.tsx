import React from "react";
import Carousel from "./components/Carousel/Carousel";
import { CarouselConfig as defaultConfig } from "./config/Carousel";
import "./App.css";

function App() {
  return <div className="App">
    <Carousel
      indicatorIsShow={defaultConfig.indicatorIsShow}
      delay={defaultConfig.delay}
      initialIndex={defaultConfig.initialIndex}
      transitionTime={defaultConfig.transitionTime}
    />
  </div>;
}

export default App;
