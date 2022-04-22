import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { CarouselItems } from './';


function App() {
  return <div className="App">
    <Carousel items={CarouselItems}></Carousel>
  </div>;
}

export default App;