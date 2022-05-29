import React from "react";
import Carousel from "./components/Carousel";
import Goods from "./components/goods";
import "./App.css";
import imgAirpods from './assets/airpods.png';
import imgIphone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';

const CarouselItem = Carousel.Item;

function App() {
  return (
    <div className="App">
      <Carousel interval={2000}>
        <CarouselItem>
          <Goods name='xPhone' desc={<div>Lots to love. Less to spend.<br />Starting at $399.</div>} imgUrl={imgIphone} theme="dark" />
        </CarouselItem>
        <CarouselItem>
          <Goods name='Tablet' desc='Just the right amount of everything.' imgUrl={imgTablet} theme="white" />
        </CarouselItem>
        <CarouselItem>
          <Goods name={<div>Buy a Tablet or xPhone for college.<br /> Get arPods.</div>} imgUrl={imgAirpods} theme="white" />
        </CarouselItem>
      </Carousel>
    </div>
  );
}

export default App;
