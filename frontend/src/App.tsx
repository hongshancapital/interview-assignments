import React from "react";
import Carousel from "./components/Carousel";
import CarouselItem from "./components/CarouselItem";
import "./App.css";

import XPhone from "./assets/iphone.png";
import Tablet from "./assets/tablet.png";
import AirPods from "./assets/airpods.png";

const data = [
  {
    bgSrc: XPhone,
    textColor: "#fff",
    title: "XPhone",
    description: "Lots to love.Less to spend.",
    price: "Starting at $399.",
  },
  {
    bgSrc: Tablet,
    textColor: "#000",
    title: "Tablet",
    description: "Just the right amount of everything.",
  },
  {
    bgSrc: AirPods,
    textColor: "#000",
    title: "Buy a Tablet or xPhone for college.<br>Get airPods.",
  },
];

function App() {
  return (
    <Carousel autoplay duration={4000}>
      {data.map((item) => (
        <CarouselItem
          bgSrc={item.bgSrc}
          textColor={item.textColor}
          title={item.title}
          description={item.description}
          price={item.price}
        />
      ))}
    </Carousel>
  );
}

export default App;
