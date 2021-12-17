import React from "react";
import Carousel from "./components/Carousel";
import CarouselItem from "./components/CarouselItem";
import "./App.css";

import XPhone from "./assets/iphone.png";
import Tablet from "./assets/tablet.png";
import AirPods from "./assets/airpods.png";

function App() {
  return (
    <Carousel autoplay duration={4000}>
      <CarouselItem
        bgSrc={XPhone}
        textColor="#fff"
        title="XPhone"
        description="Lots to love.Less to spend."
        price="Starting at $399."
      />
      <CarouselItem
        bgSrc={Tablet}
        textColor="#000"
        title="Tablet"
        description="Just the right amount of everything."
      />
      <CarouselItem
        bgSrc={AirPods}
        textColor="#000"
        title="Buy a Tablet or xPhone for college.<br>Get airPods."
      />
    </Carousel>
  );
}

export default App;
