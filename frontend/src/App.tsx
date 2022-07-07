import React from "react";
import "./App.css";
import Carousel, { ICarouselItem } from "./components/Carousel";
import iphone from "./assets/iphone.png";
import airpods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";

const list: ICarouselItem[] = [
  {
    titleList: ["xPhone"],
    descList: ["Lots to love. Less to spend.", "Starting at $399."],
    style: {
      backgroundImage: `url(${iphone})`,
      backgroundColor: "#111",
      color: "#fff",
    },
  },
  {
    titleList: ["Tablet"],
    descList: ["Just the right amount of everything."],
    style: {
      backgroundImage: `url(${tablet})`,
      backgroundColor: "#fafafa",
      color: "#000",
    },
  },
  {
    titleList: ["Buy a Tablet or xPhone for college.", " Get arPods."],
    style: {
      backgroundImage: `url(${airpods})`,
      backgroundColor: "#f1f1f3",
      color: "#000",
    },
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
