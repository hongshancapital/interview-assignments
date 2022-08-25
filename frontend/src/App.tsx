import React from "react";
import "./App.css";
import Carousel from "./Carousel";
import {CarouselData} from './shared'
    let items:Array<CarouselData>= [
      {
        key: "iphone",
        img: require("./assets/iphone.png"),
        title: "xPhone",
        subtitle: "Lots to love. Less to spend.\n Starting at $399",
        color: "white",
      },
      {
        key: "tablet",
        img: require("./assets/tablet.png"),
        title: "Tablet",
        subtitle: "Just the right amount of everything.",
        color: "black",
      },
      {
        key: "airpods",
        img: require("./assets/airpods.png"),
        title: "Buy a Tablet or xPhone for college.\nGet airPods.",
         color: "black",
      },
    ];
function App() {
  return (
    <div className="App">
      <Carousel items={items}></Carousel>
    </div>
  );
}

export default App;
