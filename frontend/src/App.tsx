import React from "react";
import "./App.scss";
import { getImages } from "./imageset";
import { Carousel } from "./components/carousel";

function App() {
  let images = getImages();
  return <div className="padding">
    <Carousel images={images} className="container"/>
  </div>;
}

export default App;
