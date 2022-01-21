import React from "react";
import "./App.scss";
import { getImages } from "./imageset";
import { Carousel } from "./components/carousel";

function App() {
  let images = getImages();
  return <div className="padding">
    <Carousel images={images} className="container" delay={3000}/>
  </div>;
}

export default App;
