import React, { FC } from "react";
import Carousel from "./Carousel";
import "./App.css";

interface Title {
  content: string;
  color: string;
}
interface CardProps {
  titleArr: Title[];
  textArr?: Title[];
  background: string;
}

const _list: CardProps[] = [
  {
    titleArr: [{ content: "xPhone", color: "#ffffff" }],
    textArr: [
      { content: "Lots to love. Less to spend.", color: "#ffffff" },
      { content: "Starting at $399.", color: "#ffffff" },
    ],
    background: require("./assets/iphone.png"),
  },
  {
    titleArr: [{ content: "Tablet", color: "#090909" }],
    textArr: [
      { content: "Just the right amount of everything.", color: "#000000" },
    ],
    background: require("./assets/tablet.png"),
  },
  {
    titleArr: [
      { content: "Buy a Tablet or xPhone for college.", color: "#000000" },
      { content: "Get arPods.", color: "#000000" },
    ],
    background: require("./assets/airpods.png"),
  },
];

const Card: FC<CardProps> = ({ background, titleArr, textArr }) => {
  return (
    <div className="my-card-container">
      <div className="my-content">
        {titleArr?.map((title, index) => (
          <div
            key={title.content}
            className="title"
            style={{
              color: title.color,
            }}
          >
            {title.content}
          </div>
        ))}
        {textArr?.map((text, index) => (
          <div
            key={text.content}
            className="text"
            style={{
              color: text.color,
            }}
          >
            {text.content}
          </div>
        ))}
      </div>
      <img className="background-img" src={background} alt="airpods" />
    </div>
  );
};

function App() {
  return (
    <div className="App">
      <Carousel className="my-carousel-container">
        {_list.map((item, index) => (
          <Card
            key={index}
            background={item.background}
            titleArr={item.titleArr}
            textArr={item.textArr}
          />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
