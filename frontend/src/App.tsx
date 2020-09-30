import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import CarouselItem from "./components/CarouselItem";
import iphoneImg from "./assets/iphone.png";
import airpodsImg from "./assets/airpods.png";
import tabletImg from "./assets/tablet.png";

function App() {
  return (
    <div className="App">
      <Carousel as="div">
        <CarouselItem background={iphoneImg} color="white">
          <h3>xPhone</h3>
          <p>Lots to love. Less to spend.</p>
          <p>Starting at $399.</p>
        </CarouselItem>
        <CarouselItem background={airpodsImg} color="black">
          <h3>Tablet</h3>
          <p>Just the right amount of everything.</p>
        </CarouselItem>
        <CarouselItem background={tabletImg} color="black">
          <h3>Buy a Tablet or xPhone for college.</h3>
          <h3>Get arPods.</h3>
        </CarouselItem>
      </Carousel>
    </div>
  );
}

export default App;
