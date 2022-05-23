import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {
  const CarouselItems = [{
    src: iphone,
    title: ["xPhone"],
    desc: ["Lots to love. Less to spend.", "Starting at $399."],
    color: 'white',
  }, {
    src: tablet,
    title: ["Tablet"],
    desc: ["Just the right amount of everything."],
  }, {
    src: airpods,
    title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
  }] 
  
  return <div className="App">
    <Carousel items={CarouselItems} time={3000}></Carousel>
  </div>;
}

export default App;
