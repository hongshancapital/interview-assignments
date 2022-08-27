import React, { useState, useEffect } from "react";
import "./App.css";
import Carousel from "./carousel";
import { CarouselConfig } from "./interface/carousel-interface";
import { getCarouselData } from './service/carousel-service'

function App() {
  const [carouselData, setCarouselData] = useState<CarouselConfig>()

  useEffect(() => {
    /** mock fetching data from backend */
    const res = getCarouselData()
    setCarouselData(res)
  }, [])

  return <div className="App">
    { carouselData ? <Carousel duration={carouselData.duration} transitionDuration={carouselData.transitionDuration} slides={carouselData.slides} /> : null }
  </div>;
}

export default App;
