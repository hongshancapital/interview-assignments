import React, { useRef } from "react";
import "./App.css";

import Carousel from './components/Carousel'

function App() {
  const carouselRef = useRef<any>()
  return (
    <Carousel
      autoplay
      ref={carouselRef}
    />
  )
}

export default App;
