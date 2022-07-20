import React from "react";
import "./App.css";
import Carousel from "./carousel";

const img1 = require('./assets/iphone.png');
const img2 = require('./assets/tablet.png');
const img3 = require('./assets/airpods.png');

function App() {
  const arr = [
    {bgColor: '#111', fontColor: '#fff', title: 'xPhone', h2: 'Lots to love', imgUrl: img1},
    {bgColor: '#FAFAFA', fontColor: '', title: 'xPhone', h2: 'Lots to love', imgUrl: img2},
    {bgColor: '#F1F1F3', fontColor: '', title: 'xPhone', h2: 'Lots to love', imgUrl: img3},
  ]
  return <div className="App">
    <Carousel
      banners={arr}
    ></Carousel>
  </div>;
}

export default App;
