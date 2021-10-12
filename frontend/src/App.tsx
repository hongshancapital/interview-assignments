import React from "react";
import "./App.css";

import Carousel from "./components/Carousel";

const data = [
  {
    id: "xPhone",
    title: "xPhone",
    describe: "Lots to love. Less to spend.",
    price: "Starting at $399",
    picture: require("./assets/iphone.png").default,
    color: "#fff",
    backgroundColor: "#343131",
  },
  {
    id: "Tablet",
    title: "Tablet",
    describe: "Just the right amount of everything.",
    price: "",
    picture: require("./assets/tablet.png").default,
    backgroundColor: "#e7e5e5",
  },
  {
    id: "airPods",
    title: "Buy a Tablet or xPhone for college. Get airPods",
    describe: "",
    price: "",
    picture: require("./assets/AirPods.png").default,
    backgroundColor: "#e7e5e5",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel dataSource={data} />
    </div>
  );
}

export default App;
