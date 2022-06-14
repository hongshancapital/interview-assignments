import React from "react";
import "./App.css";
import { Carousel } from "./components/Carousel";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";
import { Item } from "./components/Item";

const dataArray = [
  {
    title: "xPhone",
    subTitle: "Lots to love. Less to spend.\n Starting at $399.",
    image: iphoneImg,
    color: "white",
  },
  {
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    image: tabletImg,
    color: "black",
  },
  {
    title: "Buy a Tablet or xPhone for college.\n Get arPods.",
    subTitle: "",
    image: airpodsImg,
    color: "black",
  },
];

function App() {
  return (
    <div className="App">
      <Carousel className="carousel" autoplay>
        {dataArray.map((data) => (
          <Item data={data} key={data.title} />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
