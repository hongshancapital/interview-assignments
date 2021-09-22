import React from "react";
import "./App.css";

import Carousel from "./components/Carousel";

import airpodsImg from './assets/airpods.png';
import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';

function App() {
  return (
    <div className="App">
      <Carousel imgList={[iphoneImg, tabletImg, airpodsImg]} interval={3000}></Carousel>
    </div>
  );
}

export default App;
