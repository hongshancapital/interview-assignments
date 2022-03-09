import React from "react";
import "./App.css";
import Carousel from "./Carousel";
import CarouselItem from "./CarouselItem";

function App() {
  return (
    <div className="App">
      <Carousel loop={false}>
        <CarouselItem
          itemStyle={{ backgroundColor: "#111111", color: "#fff" }}
          imageUrl={require("./assets/iphone.png")}
        >
          <div className="content">
            <div className="title">xPhone</div>
            <div className="text">
              <p>Lots to love.Less to spend.</p>
              <p>Starting at $399.</p>
            </div>
          </div>
        </CarouselItem>
        <CarouselItem
          itemStyle={{ backgroundColor: "#FAFAFA" }}
          imageUrl={require("./assets/tablet.png")}
        >
          <div className="content">
            <div className="title">Tablet</div>
            <div className="text">
              <p>Just the right amount of everything.</p>
            </div>
          </div>
        </CarouselItem>
        <CarouselItem
          itemStyle={{ backgroundColor: "#F1F1F3" }}
          imageUrl={require("./assets/airpods.png")}
        >
          <div className="content">
            <div className="title">Buy a Tablet or xPhone for college.</div>
            <div className="title">Get arPods.</div>
          </div>
        </CarouselItem>
      </Carousel>
    </div>
  );
}

export default App;
