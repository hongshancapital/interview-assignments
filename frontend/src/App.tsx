import React from "react";
import "./App.css";
import Carousel from "./Carousel";
import CarouselItem from "./Carousel/Item";
import { list } from "./mock";

function App() {
  return (
    <div className="App">
      <Carousel>
        {list.map((item) => (
          <CarouselItem data={item} key={item.id} />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
