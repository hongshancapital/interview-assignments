import React from "react";
import "./App.css";
import Carousel from "./components/carousel";

import IphonePng from "./assets/iphone.png";
import TabletPng from "./assets/tablet.png";
import AirpodsPng from "./assets/airpods.png";
import { CarouselItem } from "./components/carousel/carousel-item";
import {
  CarouselInfo,
  CarouselInfoProps,
} from "./components/carousel/carousel-info";

interface DataItem extends CarouselInfoProps {
  id: string | number;
  backgroundColor: string;
}

// mock数据
const data: Array<DataItem> = [
  {
    id: "1",
    title: "xPhone",
    description: "Lots to love.Less to spend.\nStarting at $399",
    image: IphonePng,
    backgroundColor: "rgba(14,14,14)",
    color: "#fff",
  },
  {
    id: "2",
    title: "Tablet",
    description: "Just the right amount of everything.",
    image: TabletPng,
    backgroundColor: "#fff",
    color: "#000",
  },
  {
    id: "3",
    title: "Buy a Tablet or xPhone for college.\n",
    description: "",
    image: AirpodsPng,
    backgroundColor: "#eee",
    color: "#000",
  },
];

function App() {
  return (
    <div className='App'>
      <p>React Carousel</p>
      <Carousel duration={3000} height={"600px"}>
        {data.map((item) => {
          return (
            <CarouselItem
              key={item.id}
              styles={{ backgroundColor: item.backgroundColor }}
            >
              <CarouselInfo
                title={item.title || ""}
                description={item.description || ""}
                image={item.image || ""}
                color={item.color || "#000"}
              />
            </CarouselItem>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
