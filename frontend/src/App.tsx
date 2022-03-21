import React from "react";
import "./App.css";
import Carousel from "./components/carousel";
import { carouselData } from "./_mockData/carousel";

function App() {
  return (
    <div className="App">
      <Carousel carouselData={carouselData} />
    </div>
  );
}

export default App;
