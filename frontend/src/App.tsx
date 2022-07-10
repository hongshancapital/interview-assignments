import React,{useState, useEffect} from "react";
import "./App.css";
import Airpods from './assets/airpods.png'
import Iphone from './assets/iphone.png'
import Tablet from './assets/tablet.png'

import Carousel from "./Components/Carousel";

const data = [
  {
    title: 'xPhone',
    content: 'Lots to Love. Less to spend\n Starting at $399.',
    bg: 'rgb(18,15,18)',
    image: Iphone,
    textColor: '#ffffff'
  },
  {
    title: 'Tablet',
    content: 'Just the right amount of everything.',
    bg: 'rgb(252,250,252)',
    image: Tablet,
    textColor: '#000000'
  },
  {
    title: 'Buy a Tablet',
    content: 'Buy a Tablet or Xhpone for college. \n Get airPods',
    bg: 'rgb(243,242,245)',
    textColor: '#000000',
    image: Airpods
  }
]

function App() {
  return (
    <div className="App">
      <Carousel data={data} />
    </div>
  );
}

export default App;
