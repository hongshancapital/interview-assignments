import "./App.scss";
import React, { useState } from "react";
import Carousel, {
  CarouselItem,
  CarouselInfo,
} from "./views/conpents/Carousel";

// 轮播图数据
const info = [
  {
    id: 1,
    title: "xPhone",
    text: "Lots to love.Less to spend.Starting at $399.",
    image: "",
    color: "#fff",
    backgroundColor: "#000",
  },
  {
    id: 2,
    title: "Tablet",
    text: "Just the right amount of everything.",
    image: "",
    color: "#000",
    backgroundColor: "#fff",
  },
  {
    id: 3,
    title: "Buy a Tablet or xPhone for college.",
    text: "",
    image: "",
    color: "#000",
    backgroundColor: "rgb(240,240,240)",
  },
];

const App = () => {
  return (
    <Carousel>
      {info &&
        info.map((item) => {
          return (
            <CarouselItem
              key={item.id}
              styles={{
                backgroundColor: item.backgroundColor,
                color: item.color,
              }}
            >
              <CarouselInfo
                title={item.title}
                text={item.text}
                image={item.image}
              />
            </CarouselItem>
          );
        })}
    </Carousel>
  );
};

export default App;
