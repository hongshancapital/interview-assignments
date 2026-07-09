import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

const list = [
  {
    id: "1",
    titleList: ["xPhone"],
    descList: ["Lots to love. Less to spend.", "Starting at $399."],
    imageSource: iphone,
    mode: "white",
  },
  {
    id: "2",
    titleList: ["Tablet"],
    descList: ["Just the right amount of evething."],
    imageSource: tablet,
    mode: "black",
  },
  {
    id: "3",
    titleList: ["Buy a Tablet or xPhone for college.", "Get arPods."],
    descList: [],
    imageSource: airpods,
    mode: "black",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel swiperList={list} interval={3000} />
    </div>
  );
}

export default App;
