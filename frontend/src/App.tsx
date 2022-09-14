import React from "react";
import {
  Carousel,
} from "./components/Carousel";
import "./App.css";
import xPhone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

const demoData = [
  {
    id: 'xPhone',
    title: "Buy a Tablet or xPhone for college.Get airPods.",
    describe: "Lots to love.Less to spend.Starting at $399.",
    image: xPhone,
  },
  {
    id: 'Tablet',
    title: "Tablet",
    describe: "Just the right amount of everything",
    image: tablet,
  },
  {
    id: 'airPods',
    title: "Buy a Tablet or xPhone for college.Get airPods.",
    image: airpods,
  },
];

function App() {
  return (
    <div className="App">
      <Carousel source = {demoData}/>
    </div>
  );
}

export default App;
