import React, { useState } from "react";
import "./App.css";
import { Carousel, CarouselItem, Indicator } from "./Carousel";

function App() {
  const [goodsList, setGoodsList] = useState<
    {
      pic: string;
      bgColor: string;
      titleColor: string;
      subTitleColor: string;
      title: string;
      desc: string[];
    }[]
  >([
    {
      pic: require("./assets/iphone.png"),
      title: "xPhone",
      titleColor: "#ffffff",
      subTitleColor: "#ffffff",
      bgColor: "rgb(17,17,17)",
      desc: ["Lost to love. Less to spend.", "Starting at $399."],
    },
    {
      pic: require("./assets/tablet.png"),
      title: "Tablet",
      bgColor: "rgb(250,250,250)",
      titleColor: "#000000",
      subTitleColor: "#000000",
      desc: ["Just the right amount of everything."],
    },
    {
      pic: require("./assets/airpods.png"),
      bgColor: "rgb(242,242,242)",
      titleColor: "#000000",
      subTitleColor: "#000000",
      title: "Buy a Tablet or xPhone for college.\nGet arPods",
      desc: [],
    },
  ]);

  return (
    <Carousel className="poster-content">
      {goodsList.map((good, goodIndex) => (
        <CarouselItem
          key={`good-${goodIndex + 1}`}
          className="poster-item"
          style={{ backgroundColor: good.bgColor, backgroundImage: `url(${good.pic})` }}
        >
          <div className="poster-title" style={{ color: good.titleColor }}>
            <span>{good.title}</span>
          </div>
          {good.desc.map((desc, descIndex) => (
            <div
              key={`desc-${descIndex + 1}`}
              className="poster-subtitle"
              style={{ color: good.subTitleColor }}
            >
              <span>{desc}</span>
            </div>
          ))}
        </CarouselItem>
      ))}
      <Indicator />
    </Carousel>
  );
}

export default App;
