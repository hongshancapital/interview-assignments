import React from "react";
import { Carousel, CarouselItem } from "./components";
import "./App.css";

function App({ carouselItems }: { carouselItems: CarouselItem[] }) {
  return (
    <div className='App'>
      <Carousel data={carouselItems} />
    </div>
  );
}

export default App;
