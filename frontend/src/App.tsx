import React from "react";
import "./App.css";
import Carousel from "./Carousel";
import CarouselItem from "./CarouselItem";
type CarouselItemConfig = {
  key: number;
  title: string;
  color: string;
  desc?: string;
  image: string;
};

const list: CarouselItemConfig[] = [
  {
    key: 1,
    title: "xPhone",
    color: "#fff",
    desc: `Lost to love.Less to spend.
    Starting at $399. `,
    image: require("./assets/iphone.png"),
  },
  {
    key: 2,
    title: "Tablet",
    color: "#000",
    desc: `Just the right amount for everything.`,
    image: require("./assets/tablet.png"),
  },
  {
    key: 3,
    title: `Buy a Tablet or xPhone for college.
    Get arPods.`,
    color: "#000",
    image: require("./assets/airpods.png"),
  },
];

function App() {
  return (
    <div className="App">
      <div className="container">
        <Carousel interval={3000} transitionDuration={500}>
          {list.map((item) => (
            <CarouselItem {...item} />
          ))}
        </Carousel>
      </div>
    </div>
  );
}

export default App;
