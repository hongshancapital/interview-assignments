import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import airPods from "./assets/airpods.png";
import tablet from "./assets/tablet.png";

const dataInfo = [
  {
    imageUrl: iphone,
    title: "xPhone",
    subTitle: "Lots to love,Less to spend.Starting at $399.",
    fontColor: "#fff",
  },
  {
    imageUrl: tablet,
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    fontColor: "#000",
  },
  {
    imageUrl: airPods,
    title: "Buy a Tablet or xPhone for college. Get arPods.",
    fontColor: "#000",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel dataInfo={dataInfo} duration={2000} />
    </div>
  );
}

export default App;
