import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { CarouselItems } from './const';


function App() {
  return <div className="App">{/* write your component here */}</div>;
  return <div className="App">
    <Carousel items={CarouselItems}></Carousel>
  </div>;
}

export default App;