import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import { carouselData } from "./dataManager/CarouselMock";


function App() {
  return (
    <div className="App">
      <Carousel data={carouselData}></Carousel>
    </div>
  );
}

export default App;
