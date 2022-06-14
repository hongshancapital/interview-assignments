import React from "react";
import "./App.css";
import { Carousel } from "./components/Carousel";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

function App() {
  return (
    <div className="App">
      <Carousel className="carousel" autoplay>
        <img key="iphoneImg" className="img" src={iphoneImg}></img>
        <img key="tabletImg" className="img" src={tabletImg}></img>
        <img key="airpodsImg" className="img" src={airpodsImg}></img>
      </Carousel>
    </div>
  );
}

export default App;
