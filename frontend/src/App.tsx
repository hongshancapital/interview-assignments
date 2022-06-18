import React from "react";
import { pageItem, Carousel } from "./components/Carousel";
import imgAirPods from './assets/airpods.png'
import imgIphone from './assets/iphone.png'
import imgTablet from './assets/tablet.png'
import "./App.css";

const pages: pageItem[] = [
  {
    img: imgIphone,
    title: 'xPhone',
    subtitle: 'Lots to love. Less to spend.\nStarting at $399.'
  }, {
    img: imgTablet,
    title: 'tablet',
    subtitle: 'Just the right amount of everything.'
  }, {
    img: imgAirPods,
    title: 'Tablet or XPhone for college.\nGet arPods.',
    subtitle: ''
  }
]

function App() {
  return <div className="App">
    <Carousel pages={pages} delay={3000} enableManualSwitch={true}></Carousel>
  </div>;
}

export default App;
