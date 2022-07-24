import React from "react";
import "./App.css";
import Carousel, { ICarouselItem } from "./components/Carousel";

const items: ICarouselItem[] = [
  {
    classname: 'phone',
    title: "xPhone",
    text: "Lots to love.Less to spend.\nStarting at $399.",
  },
  {
    classname: 'tablet',
    title: "Tablet",
    text: "Just the right amount of everything.",
  },
  {
    classname: 'airpods',
    title: (
      <>
        <div>Buy a Tablet or xPhone for college.</div>
        <div>Get airPods.</div>
      </>
    ),
  },
];

function App() {
  return <div className="App">
    <Carousel items={items} style={{ width: 1280, height: 720 }} hoverstop={false} />
  </div>;
}

export default App;
