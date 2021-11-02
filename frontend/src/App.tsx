import React from "react";
import Carousel from "./Carousel";
import "./App.css";

interface CarouselFrameItem {
  iconType: "iphone" | "airpods" | "tablet";
  fontColor?: string;
  backgroundColor?: string;
  title?: string[];
  text?: string[];
}

const CAROUSEL_FRAME_LIST: CarouselFrameItem[] = [
  {
    backgroundColor: "#000000",
    fontColor: "#ffffff",
    iconType: "iphone",
    title: ["xPhone"],
    text: ["Lots to love. Less to spend.", "Starting at $399."],
  },
  {
    backgroundColor: "#fafafa",
    iconType: "tablet",
    title: ["Tablet"],
    text: ["Just the right amount of everything."],
  },
  {
    backgroundColor: "#f1f1f3",
    iconType: "airpods",
    title: ["Buy a Tablet or xPhone for college.", "Get airPods."],
  },
];

function App() {
  return (
    <div className="App">
      <div className="container">
        <Carousel
          options={{
            dotColor: "#757c75",
            duration: 500,
            interval: 1000
          }}
        >
          {CAROUSEL_FRAME_LIST.map((item: CarouselFrameItem, index: number) => {
            return (
              <div
                className="Frame"
                style={{
                  backgroundImage: `url(${
                    require(`./assets/${item.iconType}.png`).default
                  })`,
                  backgroundColor: item.backgroundColor,
                }}
                key={index}
              >
                {item.title &&
                  item.title.length > 0 &&
                  item.title.map((i: string, _index: number) => (
                    <div
                      className="title"
                      style={{
                        color: item.fontColor ? item.fontColor : "#000000",
                      }}
                      key={_index}
                    >
                      {i}
                    </div>
                  ))}
                {item.text &&
                  item.text.length > 0 &&
                  item.text.map((i: string, _index: number) => (
                    <div
                      className="text"
                      style={{
                        color: item.fontColor ? item.fontColor : "#000000",
                      }}
                      key={_index}
                    >
                      {i}
                    </div>
                  ))}
              </div>
            );
          })}
        </Carousel>
      </div>
    </div>
  );
}

export default App;
