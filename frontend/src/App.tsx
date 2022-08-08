import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { ICarouselItem } from "./types/carousel";

let customs: Array<ICarouselItem> = [
  {
    key: "iphone",
    title: "xPhone",
    context: ["Lots to Love.Less to spend.", "Starting at $399"],
    img: "iphone.png",
    color: "white",
  },
  {
    key: "tablet",
    title: "Tablet",
    context: ["Just the right amount of everything."],
    img: "tablet.png",
  },
  {
    key: "airpods",
    title: 'Buy a Tablet or xPhone for college", "Get airPods.',
    img: "airpods.png",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel items={customs}></Carousel>
    </div>
  );
}

export default App;
