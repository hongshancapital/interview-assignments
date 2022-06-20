import React from "react";
import "./App.css";

import Carousel from "./components/carousel";

import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

const content = [
  {
    title: 'xPhone',
    text: 'Lots to love. Less to spend.\nStarting at $399.',
    fontColor: 'white',
    imgUrl: iphone,
  },
  {
    title: 'Tablet',
    text: 'Just the right amount of everything.',
    fontColor: 'black',
    imgUrl: tablet,
  },
  {
    title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
    text: '',
    fontColor: 'black',
    imgUrl: airpods,
  },
]

function App() {
  return <div className="App">
    <Carousel content={content} switchTime={3000}></Carousel>
  </div>;
}

export default App;
