import React from "react";
import Carousel from "./Carousel";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";
import "./App.css";

function App() {
  const list = [
    {
      id: 1,
      title: "xPhone",
      description: "Lots to love. Less to spend.\n Starting at $399.",
      image: iphone,
      color: "#FFFFFF",
    },
    {
      id: 2,
      title: "Tablet",
      description: "Just the right amount of everything.",
      image: tablet,
      color: "#000000",
    },
    {
      id: 3,
      title: "Buy a Tablet or xPhone for college.\n Get airPods.",
      image: airpods,
      color: "#000000",
    }
  ];

  return (
    <div className="App">
      <Carousel list={list} />
    </div>
  );
}

export default App;
