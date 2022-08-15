import Carousel from "components/Carousel";
import React from "react";
import "./App.css";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

function App() {
  const CarouselData: CarouselItem[] = [
    {
      background: "#000",
      title: "xPhone",
      desc: ["lots to love. Less to speed.", " Starting at $399."],
      pic: iphone,
      color: "#fff",
    },
    {
      background: "#fff",
      title: "Tablet",
      desc: "Just the right amount of everything.",
      pic: tablet,
    },
    {
      background: "#fff",
      title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
      pic: airpods,
    },
  ];
  return (
    <div className="App">
      <Carousel data={CarouselData} />
    </div>
  );
}

export default App;
