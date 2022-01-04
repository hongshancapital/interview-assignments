import React from "react";

// components
import Carousel from "./components/Carousel";

// assets
import "./App.css";
import IPhone from "./assets/iphone.png";
import Tablet from "./assets/tablet.png";
import AirPods from "./assets/airpods.png";

const data = [
  {
    imgUrl: IPhone,
    color: "#FFFFFF",
    title: "XPhone",
    description: "Lots to love.Less to spend.",
    price: "Starting at $399.",
  },
  {
    imgUrl: Tablet,
    color: "#000000",
    title: "Tablet",
    description: "Just the right amount of everything.",
  },
  {
    imgUrl: AirPods,
    color: "#000000",
    title: "Buy a Tablet or xPhone for college.<br>Get airPods.",
  },
];

function App() {
  return (
    <div className="App">{
      /* write your component here */
      <Carousel duration={3000} data={data} />
    }</div>
  )
}

export default App;
