import "./App.css";
import React from "react";

import pic1 from "./assets/airpods.png";
import pic2 from "./assets/iphone.png";
import pic3 from "./assets/tablet.png";

import Carousel, { CarouselItem, CarouselInfo } from "./Carousel"

type itemType = {
  title?: string
  describe?: string
  image?: string
  id?: number
  color?: string
}
// 轮播图数据
const info: itemType[] = [
  {
    id: 1,
    title: "xPhone",
    describe: "Lots to love. Less to spend. Starting at $399",
    image: pic1
    // backgroundColor: "#425066",
  },
  {
    id: 2,
    title: "Tablet",
    describe: "Just the right amount of everything",
    image: pic2,
    color: "#fff"
  },
  {
    id: 3,
    title: "Buy a Tablet or xPhone for college. Get arPods",
    describe: "",
    image: pic3
  }
];

function App() {
  return (
        <Carousel>
          {info.map((item) => {
            return (
              <CarouselItem
                key={item.id}
              >
                <CarouselInfo
                  title={item.title}
                  describe={item.describe}
                  image={item.image}
                  color={item.color}
                />
              </CarouselItem>
            );
          })}
        </Carousel>
        // <div></div>
  );
};

export default App;

