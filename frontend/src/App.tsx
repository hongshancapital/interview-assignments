import React from "react";
import "./App.css";
import Carousel from "./carousel/index";
import CarouselItem, { ICarouselItem } from "./carouselItem";

const carouselList: ICarouselItem[] = [
  {
    style: {
      backgroundImage: `url(${require("./assets/iphone.png")})`,
      color: 'white',
    },
    title: ['xPhone'],
    desc: [
      "Lots to love. Less to spend.",
      "Starting at $399.",
    ],
  },
  {
    style: {
      backgroundImage: `url(${require("./assets/tablet.png")})`
    },
    title: ['Tablet'],
    desc: [
      "Just the right amount of everything."
    ],
  },
  {
    style: {
      backgroundImage: `url(${require("./assets/airpods.png")})`
    },
    title: ["Buy a Tablet or xPhone for college", "Get arPods."],
    desc: []
  },
];

const App = () => {

  return (
    <div className="App">
      <Carousel
        style={{ width: "100%", height: "100vh" }}
      >
        {carouselList.map((item, index) => (<CarouselItem {...item} key={index} />))}
      </Carousel>
    </div>
  );
}

export default App;
