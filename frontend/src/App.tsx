import React from "react";
import Carousel from "./components/Carousel";
import Goods from "./components/goods";
import { GOODS_LIST } from "./constant";
import "./App.css";

const CarouselItem = Carousel.Item;

function App() {
  return (
    <div className="App">
      <Carousel interval={2000}>
        {GOODS_LIST.map(({ name, desc = '', bg, theme }, index) => (
          <CarouselItem key={index}>
            <Goods name={name} desc={desc} bg={bg} theme={theme} />
          </CarouselItem>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
