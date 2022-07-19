import React from "react";
import "./App.css";
import { Carousel } from "./components/carousel";
import xPhone from './assets/iphone.png';
import Tablet from './assets/tablet.png';
import Pods from './assets/airpods.png';

// 轮播图设置
const sliderContents = [
  {
    title: <p className="title">xPhone</p>,
    desc: 
      <div className="desc">
        <p>Lots to love.Less to spend.</p>
        <p>Starting at $399.</p>
      </div>
    ,
    url: xPhone,
    bgColor: '#111111',
    textColor: '#fff',
  },
  {
    title: <p className="title">Tablet</p>,
    desc: 
      <div className="desc">
        <p>Just the right amount of everything.</p>
      </div>
    ,
    url: Tablet,
    bgColor: '#fafafa',
  },
  {
    title: 
      <div className="title">
        <p>Buy a Tablet or xPhone for college.</p>
        <p>Get Airpods.</p>
      </div>
      ,
    url: Pods,
    bgColor: '#F1F1F3',
  },
]


function App() {
  return (
    <div className="App">
      <Carousel items={sliderContents} duration={3000}></Carousel>
    </div>
  );
}

export default App;
