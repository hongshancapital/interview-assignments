import React from "react";
import "./App.css";
import { Carousel } from "./carousel";
import { ICarouselProps } from "./carousel/type";
import iPhone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airPods from "./assets/airpods.png";

const data: ICarouselProps["data"] = [
  {
    title: ["xPhone"],
    bgColor: "rgba(17,17,17,255)",
    description: ["Lots to love. Less to spend.", "Starting at $399."],
    textColor: "#fafafa",
    image: iPhone,
  },
  {
    title: ["Tablet"],
    description: ["Just the right amount of everything."],
    image: tablet,
    bgColor: "#FAFAFA",
    imageScaleRate: 2,
  },
  {
    title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
    image: airPods,
    bgColor: "#f0f0f2",
    imageScaleRate: 2,
  },
];

function App() {
  return (
    <div className="App">
      <Carousel data={data} width={"100vw"} height={"100vh"} />
    </div>
  );
}

export default App;
