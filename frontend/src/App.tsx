import React from "react";

// components
import Carousel from "./components/carousel";

// assets
import "./App.css";

// mock data
import { CarouselData } from "./mock/carousel"


function App() {
  return (
    <div className="App">{
      /* write your component here */
      <Carousel duration={3000} data={CarouselData} />
    }</div>
  )
}

export default App;
