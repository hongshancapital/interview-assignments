import React from "react";
import "./App.css";
import Carousel, { ICarouselItem } from "./components/Carousel";

import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

const items: ICarouselItem[] = [
  {
    bg: iphone,
    bgColor: "#111",
    textColor: "#ffffff",
    title: "xPhone",
    text: "Lots to love.Less to spend.\nStarting at $399.",
  },
  {
    bg: tablet,
    bgColor: "#fafafa",
    textColor: "#000",
    title: "Tablet",
    text: "Just the right amount of everything.",
    backgroundSize: "cover",
  },
  {
    bg: airpods,
    bgColor: "#f1f1f1",
    textColor: "#000",
    title: (
      <>
        <div>Buy a Tablet or xPhone for college.</div>
        <div>Get airPods.</div>
      </>
    ),
    backgroundSize: "cover",
  },
];

function App() {
  return <div className="App">
    <Carousel items={items} style={{ width: 1280, height: 720 }} hoverstop={false} />
  </div>;
}

export default App;
