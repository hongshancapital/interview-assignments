import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { ICarouselItem } from "./components/CarouselItem";
import iphone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";

const list: ICarouselItem[] = [
  {
    titleList: ["xPhone"],
    descList: ["Lots to love. Less to spend.", "Starting at $399."],
    image: iphone,
    backgroundColor: "#111",
    textColor: "#fff",
  },
  {
    titleList: ["Tablet"],
    descList: ["Just the right amount of everything."],
    image: tablet,
    backgroundColor: "#fafafa",
    textColor: "#000",
  },
  {
    titleList: ["Buy a Tablet or xPhone for college.", " Get arPods."],
    image: airpods,
    backgroundColor: "#f1f1f3",
    textColor: "#000",
  },
];

function App() {
  return (
    <div className="App">
      <div className="carousel">
        <Carousel list={list} />
      </div>
    </div>
  );
}

export default App;
