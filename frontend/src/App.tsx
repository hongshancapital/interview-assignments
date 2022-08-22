import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import CarouselItem from "./components/CarouselItem";



function App() {
  return <div className="App">
    <Carousel perStayTime={4000} perAniTime={300} initcialIndex={0} autoPlay={true}>
      <CarouselItem>
        <div className="pannel__one">
          <h1 className="pannel__title">xPhone</h1>
          <h4>Lots to love.Less to spend. Starting at $399.</h4>
        </div>

      </CarouselItem>
      <CarouselItem>
        <div className="pannel__two">
          <h1 className="pannel__title">Tablet</h1>
          <h4>Just the right amount of everything.</h4>
        </div>

      </CarouselItem>
      <CarouselItem>
        <div className="pannel__three">
          <h1 className="pannel__title">
            Buy a Tablet or xPhone for college. Get arPods.
          </h1>
        </div>

      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
