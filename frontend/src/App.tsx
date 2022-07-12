import React from "react";
import "./App.scss";
import Carousel, { CarouselSlide } from "./components/carousel";

function App() {
  return (
    <div className="App">
      <Carousel duration={3000}>
        <CarouselSlide className="tab1 colr1">
          <div className="title">xPhone</div>
          <div className="text">Lots to love.Less to spend.</div>
          <div className="text">Strating at $399.</div>
        </CarouselSlide>
        <CarouselSlide className="tab2">
          <div className="title">Tablet</div>
          <div className="text">Just the right amount of everything</div>
        </CarouselSlide>
        <CarouselSlide className="tab3">
          <div className="title">Buy a Tablet or xPhone for college.</div>
          <div className="title">Get arPods</div>
        </CarouselSlide>
      </Carousel>
    </div>
  );
}

export default App;
