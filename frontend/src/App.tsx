import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

const carouselItems = [
  {
    id: 1,
    title: ["xPhone"],
    description: ["Lots to love.Less to spend.", "Starting at $399."],
    style: {
      backgroundColor: "#111111",
      color: "#ffffff",
      background: `url(${require("./assets/iphone.png")}) center bottom / cover no-repeat`,
      maxWidth: "100%",
    },
  },
  {
    id: 2,
    title: ["Tablet"],
    description: ["Just the right amount of everything."],
    style: {
      backgroundColor: "#fafafa",
      color: "#000000",
      background: `url(${require("./assets/tablet.png")}) center bottom / cover no-repeat`,
    },
  },
  {
    id: 3,
    title: ["Buy a Tablet or xPhone for college.", "Get AirPods."],
    description: [],
    style: {
      backgroundColor: "#fafaf3",
      color: "#000000",
      background: `url(${require("./assets/airpods.png")}) center bottom / cover no-repeat`,
    },
  },
];

function App() {
  return (
    <div className="App">
      <Carousel autoplay duration={3000}>
        {carouselItems.map((item) => (
          <div key={item.id} className="box" style={item.style}>
            <div className="height-quarter"></div>
            {item.title.map((title) => (
              <p key={title} className="title">
                {title}
              </p>
            ))}
            {item.description.map((description) => (
              <p key={description} className="text">
                {description}
              </p>
            ))}
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
