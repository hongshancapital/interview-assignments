import React from "react";
import "./App.css";

import Carousel from './components/Carousel';

const carouselItems = [
  {
    title: "xPhone",
    describe: "Lots to love. Less to spend.\n Starting at $399.",
    className: 'carousel-iphone'
  },
  {
    title: "Tablet",
    describe: "Just the right amount of everything.",
    className: 'carousel-tablet'
  },
  {
    title: "Buy a tablet or xPhone for college.\n Get airPods",
    describe: "",
    className: 'carousel-airpods'
  }
];

function App() {
  return <div className="App">
    <Carousel
      items={carouselItems}
      duration={2000}
      speed={300}
    />
  </div>;
}

export default App;
