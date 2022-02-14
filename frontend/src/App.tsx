import React from "react";
import { Carousel, SlideItem } from "./components/Carousel";
import "./App.css";

//#region  slides components defined later.

const slides: SlideItem[] = [
  {
    id: "slide01",
    bgColor: "#111",
    color: "#fff",
    imgTop: "0rem",
    img: require("./assets/iphone.png"),
    paragraph: ` <h3>xPhone</h3><p>Lots to love. Less to spend.</p><p>Starting at $399.</p>`,
  },
  {
    id: "slide02",
    bgColor: "#fafafa",
    color: "#000",
    imgTop: "-4rem",
    img: require("./assets/tablet.png"),
    paragraph: `        <h3>Tablet</h3>
    <p>Just the right amount of everything</p>`,
  },
  {
    id: "slide03",
    bgColor: "#f1f1f3",
    color: "#444",
    imgTop: "-4rem",
    img: require("./assets/airpods.png"),
    paragraph: `         <h3>Buy a Tablet or XPhone for college.</h3>
    <h3>Get airPods</h3>`,
  },
];

//#endregion

function App() {
  return (
    <div className="App">
      <Carousel slides={slides} timingDur={6000} />
    </div>
  );
}

export default App;
