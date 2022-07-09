import React from "react";
import Carousel, { ICarouselList } from "./components/Carousel";
import "./App.css";
import airpods from "./assets/airpods.png";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";

function App() {
  const carouselList: ICarouselList[] = [
    {
      imgUrl: iphone,
      title: ["xPhone"],
      text: ["Lots to love. Less to spend.", "Starting at $399"],
      contentStyle: { color: "white" },
    },
    {
      imgUrl: tablet,
      title: ["Tablet"],
      text: ["Just the right amout of everything."],
    },
    {
      imgUrl: airpods,
      title: ["Buy a Tablet or xPhone for college.", "Get arPods"],
      text: [""],
    },
  ];

  return (
    <div className="App">
      <div className="carousel-container">
        <Carousel carouselList={carouselList} />
      </div>
    </div>
  );
}

export default App;
