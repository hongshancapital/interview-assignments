import React from "react";
import Carousel from "./components/carousel"
import { slideItem } from "./components/carousel/defination";
import "./App.css";

function App() {
  const carouselData: slideItem[] = [
    {
      theme: 'dark',
      title: 'xPhone',
      description: 'Lots to love. Less to spend.\nStarting at $399.',
      picture: '/iphone.png',
    },
    {
      theme: 'light',
      title: 'Tablet',
      description: 'Just the right amount of everything.',
      picture: '/tablet.png',
    },
    {
      theme: 'light',
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
      picture: '/airpods.png',
    },
  ];
  return <div className="App">
    <Carousel items={carouselData}></Carousel>
  </div>;
}

export default App;
