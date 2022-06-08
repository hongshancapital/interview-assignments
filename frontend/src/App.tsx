import React from "react";
import { Carousel, CarouselSlide } from "./components/Carousel"; 
import "./App.css";

import aripodsImage from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

function App() {
  return (
    <div className="App">
      <Carousel>
        <CarouselSlide>1</CarouselSlide>
        <CarouselSlide>2</CarouselSlide>
        <CarouselSlide>3</CarouselSlide>
      </Carousel>
    </div>
  );
}

export default App
