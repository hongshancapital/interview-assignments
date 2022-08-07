import React from "react";
import "./App.css";
import iphone from './assets/iphone1.png';
import tablet from './assets/tablet1.png';
import airpods from './assets/airpods1.png';
import Carousel from './components/carousel/Carousel';
import CarouselItem from './components/carousel/CarouselItem';

function App() {
  return <div className="App">
    <Carousel>
      <CarouselItem className="box iphone-box">
        <div className="iphone-text">
          <p className="title">xPhone</p>
          <p className="text">Lots to love. Less to spend.</p>
          <p className="text">Starting at $399.</p>
        </div>
        <img className="iphone-img" src={iphone} alt="iphone" />
      </CarouselItem>
      <CarouselItem className="box tablet-box">
      <div className="tablet-text">
          <p className="title">Tablet</p>
          <p className="text">Just the right amount of everything.</p>
        </div>
        <img className="tablet-img" src={tablet} alt="iphone" />
      </CarouselItem>
      <CarouselItem className="box airpods-box">
      <div className="airpods-text">
          <p className="title">Buy a Tablet or xPhone for college.</p>
          <p className="title">Get airPods.</p>
        </div>
        <img className="airpods-img" src={airpods} alt="iphone" />
      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
