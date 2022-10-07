import React from "react";
import Carousel from "./components/carousel";
import "./App.css";
import { ICarouselInf } from "./types/modelTypes";

import tablet from './assets/tablet.png';
import xPhone from './assets/iphone.png';
import airpods from './assets/airpods.png';

/** 获取数据方法 */
function getData(): ICarouselInf[] {
  return [
    {
      id: 0,
      title: "xPhone",
      description: "Lots to love.Less to spend.\nStarting at $399.",
      imageUrl: xPhone,
      link: '',
      color: '#fff'
    },
    {
      id: 1,
      title: "Tablet",
      description: "Just the right amount of everything",
      imageUrl: tablet,
      link: '',
      color: '#000'
    },
    {
      id: 2,
      title: "Buy a Tablet or xPhone for college.\nGet airPods.",
      description: '',
      imageUrl: airpods,
      link: '',
      color: '#000'
    }
  ]
}

function App() {
  return (
    <div className="App">
      <Carousel data={getData()} />
    </div>
  );
}

export default App;
