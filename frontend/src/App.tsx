import React from "react";
import "./App.css";
import Carousel from "./carousel";
import { mockData } from './mock/carousel-mock'

function App() {

  return <div className="App">
    { mockData ? <Carousel duration={mockData.duration} transitionDuration={mockData.transitionDuration} slides={mockData.slides} /> : null }
  </div>;
}

export default App;
