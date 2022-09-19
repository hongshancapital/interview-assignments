import React from "react";
import "./App.css";
import Carousel from "./components/carousel";

import IphonePng from "./assets/iphone.png";
import TabletPng from "./assets/tablet.png";
import AirpodsPng from "./assets/airpods.png";

const data = [
  {
    title: "xPhone",
    description: "Lots to love.Less to spend.\nStarting at $399",
    image: IphonePng,
    backgroundColor: "rgba(14,14,14)",
    color: "#fff",
  },
  {
    title: "Tablet",
    description: "Just the right amount of everything.",
    image: TabletPng,
    backgroundColor: "rgb(249,249,249)",
    color: "#000",
  },
  {
    title: "Buy a Tablet or xPhone for college.\n",
    image: AirpodsPng,
    backgroundColor: 'rgba(237,237,240)',
    color: "#000",
  },
];

function App() {
  return <div className='App'>
    <Carousel data={data} duration={3000} height={'600px'}/>
  </div>;
}

export default App;
