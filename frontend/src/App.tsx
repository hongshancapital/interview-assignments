import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import carouselItems from './data/carousel'

function App() {
  return (
    <div className="App">
      <Carousel className="App-carousel" items={carouselItems}></Carousel>
    </div>
  );
}

export default App;
