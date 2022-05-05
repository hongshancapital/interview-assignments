import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

const items = [
  {
    titles: ["xPhone"],
    texts: ["Lots to love. Less to spend.", "Starting at $399."],
    color: "#fff",
    backgroundImageURL: require("./assets/iphone.png"),
  },
  {
    titles: ["Tablet"],
    texts: ["Just the right amount of everything."],
    backgroundImageURL: require("./assets/tablet.png"),
  },
  {
    titles: ["Buy a Tablet or xPhone for college.", "Get arPods."],
    backgroundImageURL: require("./assets/airpods.png"),
  },
];

function App() {
  return (
    <div className="App" data-testid="App">
      <Carousel items={items}></Carousel>
    </div>
  );
}

export default App;
