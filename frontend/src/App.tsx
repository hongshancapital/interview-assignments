import React from "react";
import "./App.css";
import Carousel from "./components/Carousel/Carousel";
import { carouselItem } from "./components/Carousel/CarouselModel";

function App() {

  // This should import from config file which can modify easily
  const carouselItems:carouselItem[] = [
      {
          titles: ["xPhone"],
          descriptions: ["Lots to love.Less to spend.", "Starging at $399."],
          icon: "/sliceBackground/iphone.png",
          fontColor: "white",
          backgroundColor: "#111111",
          indicator: {
            backgroundColor: "#9b9b9b9b",
            processColor: "#f9f9f9f9",
          },
      },
      {
          titles: ["Tablet"],
          descriptions: ["Just the right amount of everything."],
          icon: "/sliceBackground/tablet.png",
          backgroundColor: "#fafafafa",
          indicator: {
            backgroundColor: "#fafafafa",
            processColor: "#a2a2a2",
          },
      },
      {
          titles: ["Buy a Tablet or xPhone for college.", "Get airPods."],
          descriptions: [],
          icon: "/sliceBackground/airpods.png",
          backgroundColor: "#f0f0f2",
          indicator: {
            backgroundColor: "#fafafafa",
            processColor: "#a9a9a9",
          },
      }
  ]

  return <div className="App">
    {/* write your component here */}
    <Carousel carouselItems={carouselItems} ></Carousel>
    </div>;
}

export default App;
