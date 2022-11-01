import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'

const carouselItems = [
  {
    title: "xPhone",
    subTitle: "Lots to love. Less to spend.\n Starting at $399.",
    image: iphoneImg,
    className: 'item-1'
  },
  {
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    image: tabletImg,
    className: 'item-2'
  },
  {
    title: "Buy a tablet or xPhone for college.\n Get airPods",
    subTitle: "",
    image: airpodsImg,
    className: 'item-3'
  }
]

function App() {
  return <div className="App">
    <Carousel items={carouselItems} duration={4000} speed={400}/>
  </div>;
}

export default App;
